package com.saveme.ledger.service.process;



import com.saveme.ledger.service.logic.FridgeWarningPolicy;
import com.saveme.ledger.domain.Category;
import com.saveme.ledger.dto.request.ExpenseSimulationRequestDto;
import com.saveme.ledger.dto.response.BudgetDashboardResponseDto;
import com.saveme.ledger.dto.response.ExpenseSimulationResponseDto;
import com.saveme.ledger.repository.CategoryRepository;
import com.saveme.ledger.service.query.BudgetQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetProcessService {

    private final BudgetQueryService budgetQueryService;
    private final CategoryRepository categoryRepository;
    private final FridgeWarningPolicy fridgeWarningPolicy;

    @Transactional(readOnly = true)
    public ExpenseSimulationResponseDto simulateExpense(Long memberId, ExpenseSimulationRequestDto request) {
        // 데이터 준비
        BudgetDashboardResponseDto currentStatus = budgetQueryService.getDashboardData(
            memberId, YearMonth.from(request.date()).toString());
        Category category = categoryRepository.findById(request.categoryId()).orElse(null);

        // 예산 시뮬레이션 계산
        long spendingAmount = request.amount();
        Long currentDailyBudget = currentStatus.getDailyBudget();
        long futureTotalBalance = currentStatus.getCurrentBalance() - spendingAmount;
        long remainingDays = currentStatus.getRemainingDays();

        // 내일 기준 하루 예산 계산
        long daysFromTomorrow = Math.max(0, remainingDays - 1);
        Long futureDailyBudget = (daysFromTomorrow > 0) ? (futureTotalBalance / daysFromTomorrow) : futureTotalBalance;

        // 경고 여부 판단
        boolean isBudgetWarning = (remainingDays > 1 && currentDailyBudget > 0) && (spendingAmount > currentDailyBudget);

        // 냉장고 경고 분기 처리
        String fridgeItemsStr = fridgeWarningPolicy.checkFridgeWarning(memberId, category);
        boolean isFridgeWarning = !fridgeItemsStr.isEmpty();

        if (!isBudgetWarning && !isFridgeWarning) {
            return ExpenseSimulationResponseDto.builder().isWarning(false).build();
        }

        String message = createSimulationMessage(isBudgetWarning, isFridgeWarning, fridgeItemsStr, futureDailyBudget);

        return ExpenseSimulationResponseDto.builder()
            .isWarning(true)
            .currentDailyBudget(currentDailyBudget)
            .futureDailyBudget(futureDailyBudget)
            .budgetReduction(currentDailyBudget - futureDailyBudget)
            .message(message)
            .build();
    }

    // 메시지 생성 로직
    private String createSimulationMessage(boolean budgetWarn, boolean fridgeWarn, String items, Long nextDayBudget) {
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
}