package com.saveme.ledger.service.process;

import com.saveme.ledger.dto.request.ExpenseSimulationRequestDto;
import com.saveme.ledger.dto.response.BudgetDashboardResponseDto;
import com.saveme.ledger.dto.response.ExpenseSimulationResponseDto;
import com.saveme.ledger.service.query.BudgetQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class BudgetProcessService {

    private final BudgetQueryService budgetQueryService;

    public ExpenseSimulationResponseDto simulateExpense(Long memberId, ExpenseSimulationRequestDto request) {
        String yearMonthStr = YearMonth.from(request.date()).toString();
        BudgetDashboardResponseDto currentStatus = budgetQueryService.getDashboardData(memberId, yearMonthStr);

        Long currentDailyBudget = currentStatus.getDailyBudget();
        long remainingDays = currentStatus.getRemainingDays();

        if (remainingDays <= 1 || currentDailyBudget <= 0) {
            return ExpenseSimulationResponseDto.builder()
                .isWarning(false)
                .build();
        }

        // 시뮬레이션 로직
        long spendingAmount = request.amount();

        boolean isWarning = spendingAmount > currentDailyBudget;

        Long futureBalance = currentStatus.getCurrentBalance() - spendingAmount;

        long futureRemainingDays = remainingDays - 1;

        Long futureDailyBudget = futureBalance / futureRemainingDays;

        Long budgetReduction = currentDailyBudget - futureDailyBudget;

        String message = String.format("오늘 %s원을 더 쓰시면, 오늘 예산을 초과하며 내일부터 하루 예산이 %s원 줄어듭니다.",
            String.format("%,d", spendingAmount),
            String.format("%,d", budgetReduction));

        return ExpenseSimulationResponseDto.builder()
            .isWarning(isWarning)
            .currentDailyBudget(currentDailyBudget)
            .futureDailyBudget(futureDailyBudget)
            .budgetReduction(budgetReduction)
            .message(message)
            .build();
    }
}