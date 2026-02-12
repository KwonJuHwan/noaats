package com.saveme.ledger.service.process;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.domain.InventoryStatus;
import com.saveme.consumption.repository.InventoryRepository;
import com.saveme.ledger.domain.Category;
import com.saveme.ledger.dto.request.ExpenseSimulationRequestDto;
import com.saveme.ledger.dto.response.BudgetDashboardResponseDto;
import com.saveme.ledger.dto.response.ExpenseSimulationResponseDto;
import com.saveme.ledger.repository.CategoryRepository;
import com.saveme.ledger.service.query.BudgetQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetProcessService {

    private final BudgetQueryService budgetQueryService;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;

    private final Map<String, Boolean> dailyFridgeWarningCache = new ConcurrentHashMap<>();

    @Transactional(readOnly = true)
    public ExpenseSimulationResponseDto simulateExpense(Long memberId, ExpenseSimulationRequestDto request) {
        // 1. 현재 예산 상태 조회
        String yearMonthStr = YearMonth.from(request.date()).toString();
        BudgetDashboardResponseDto currentStatus = budgetQueryService.getDashboardData(memberId, yearMonthStr);

        Long currentDailyBudget = currentStatus.getDailyBudget();
        long remainingDays = currentStatus.getRemainingDays();

        // 2. 예산 시뮬레이션 계산
        long spendingAmount = request.amount();
        long futureTotalBalance = currentStatus.getCurrentBalance() - spendingAmount;
        long daysFromTomorrow = remainingDays - 1;

        Long futureDailyBudget = (daysFromTomorrow > 0) ? (futureTotalBalance / daysFromTomorrow) : futureTotalBalance;
        Long budgetReduction = currentDailyBudget - futureDailyBudget;
        // 예산 초과 여부 확인
        boolean isBudgetWarning = false;
        if (remainingDays > 1 && currentDailyBudget > 0) {
            isBudgetWarning = spendingAmount > currentDailyBudget;
        }
        // 냉장고 파먹기 여부 확인
        boolean isFridgeWarning = false;
        String fridgeItemsStr = "";

        Category category = categoryRepository.findById(request.categoryId()).orElse(null);

        if (isFoodButNotGrocery(category)) {
            String cacheKey = memberId + ":" + LocalDate.now();

            // 오늘 이미 경고했는지 확인
            if (!dailyFridgeWarningCache.containsKey(cacheKey)) {
                LocalDate threeDaysLater = LocalDate.now().plusDays(3);
                // 유통기한 3일 이내 남은 보관중인 재료 조회
                List<Inventory> expiringItems = inventoryRepository.findExpiringIngredients(
                    memberId, InventoryStatus.IN_STORE, threeDaysLater);

                if (!expiringItems.isEmpty()) {
                    isFridgeWarning = true;
                    // 재료 이름 최대 2개까지만 나열
                    fridgeItemsStr = expiringItems.stream()
                        .limit(2)
                        .map(Inventory::getName)
                        .collect(Collectors.joining(", "));

                    if (expiringItems.size() > 2) {
                        fridgeItemsStr += " 외 " + (expiringItems.size() - 2) + "건";
                    }

                    // 경고 캐시에 저장
                    dailyFridgeWarningCache.put(cacheKey, true);
                }
            }
        }
        if (!isBudgetWarning && !isFridgeWarning) {
            return ExpenseSimulationResponseDto.builder().isWarning(false).build();
        }
        String message = buildWarningMessage(isBudgetWarning, isFridgeWarning, spendingAmount, futureDailyBudget, fridgeItemsStr);

        return ExpenseSimulationResponseDto.builder()
            .isWarning(true)
            .currentDailyBudget(currentDailyBudget)
            .futureDailyBudget(futureDailyBudget)
            .budgetReduction(budgetReduction)
            .message(message)
            .build();
    }

    // 경고 메시지 조합
    private String buildWarningMessage(boolean budgetWarn, boolean fridgeWarn, Long spendingAmount, Long nextDayBudget, String items) {
        String nextDayBudgetStr = String.format("%,d", nextDayBudget);

        if (fridgeWarn && budgetWarn) {
            return String.format("🛑 냉장고에 [%s] 등이 남아있어요!\n게다가 오늘 예산을 초과하여 내일부터 하루 예산이 [%s원]으로 줄어듭니다.\n정말 지출하시겠습니까?",
                items, nextDayBudgetStr);
        }

        if (fridgeWarn) {
            return String.format("🛑 잠깐! 냉장고에 [%s] 등이 있어요!\n집에 있는 재료를 먼저 드시는 건 어떨까요?", items);
        }

        return String.format("오늘 예산을 초과하셨네요! 이대로 지출하시면 내일부터 하루 예산은 [%s원]이 됩니다.\n계속하시겠습니까?",
            nextDayBudgetStr);
    }

    // 식비이지만 장보기가 아닌지 체크
    private boolean isFoodButNotGrocery(Category category) {
        if (category == null) return false;
        boolean isFoodRelated = "식비".equals(category.getName()) ||
            (category.getParent() != null && "식비".equals(category.getParent().getName()));
        boolean isGrocery = "장보기".equals(category.getName());
        return isFoodRelated && !isGrocery;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void clearWarningCache() {
        dailyFridgeWarningCache.clear();
        log.info("냉장고 경고 캐시가 초기화되었습니다.");
    }
}