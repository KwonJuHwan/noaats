package com.saveme.ledger.service.query;

import com.saveme.ledger.dto.response.BudgetDashboardResponseDto;
import com.saveme.ledger.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetQueryService {

    private final IncomeQueryService incomeQueryService;
    private final FixedCostQueryService fixedCostQueryService;
    private final ExpenseQueryService expenseQueryService;
    private final ExpenseRepository expenseRepository;


    public BudgetDashboardResponseDto getDashboardData(Long memberId, String yearMonthStr) {
        YearMonth viewedMonth = (yearMonthStr == null) ? YearMonth.now() : YearMonth.parse(yearMonthStr);
        YearMonth currentMonth = YearMonth.now();
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = viewedMonth.atDay(1);

        Long totalIncome = incomeQueryService.getMonthlyIncomeTotal(memberId, firstDayOfMonth);
        Long totalFixedCost = fixedCostQueryService.getTotalFixedCost(memberId);
        Long totalGeneralExpense = expenseQueryService.getMonthlyExpenseTotal(memberId, firstDayOfMonth);
        Long totalExpense = totalFixedCost + totalGeneralExpense;
        Long currentBalance = totalIncome - totalExpense;

        Long dailyBudget = 0L;
        String status = "NORMAL";
        String message = "";
        long remainingDays = 0;

        // 과거 / 현재 / 미래 분기 처리
        if (viewedMonth.isBefore(currentMonth)) {
            status = "PAST_MONTH";
            message = "지나간 달의 지출 내역입니다. 총 지출을 확인해보세요.";
            dailyBudget = 0L;

        } else if (viewedMonth.isAfter(currentMonth)) {
            status = "FUTURE_MONTH";
            message = "승진하셨나요? 미래의 예산을 미리 보고 계십니다.";
            dailyBudget = 0L;

        } else {
            // 현재 -> 생존 예산 로직 적용
            LocalDate lastDayOfMonth = viewedMonth.atEndOfMonth();
            remainingDays = java.time.temporal.ChronoUnit.DAYS.between(today, lastDayOfMonth) + 1;
            Long todayExpense = expenseRepository.sumAmountByMemberIdAndDate(memberId, today);

            if (currentBalance < 0) {
                status = "DEFICIT";
                dailyBudget = currentBalance;
                message = "이미 적자 상태입니다! 지출을 줄이세요.";
            } else if (remainingDays <= 1) {
                status = "END_OF_MONTH";
                dailyBudget = currentBalance;
                message = "이번 달 마지막 날입니다.";
            } else {
                // 체감형 예산 계산
                Long balanceStartOfDay = currentBalance + todayExpense;
                Long baseDailyBudget = balanceStartOfDay / remainingDays;
                dailyBudget = baseDailyBudget - todayExpense;

                if (dailyBudget < 0) {
                    status = "DEFICIT";
                    message = "오늘 예산을 초과했습니다! 내일 예산이 줄어듭니다.";
                } else {
                    message = "오늘 사용 가능한 금액입니다.";
                }
            }
        }

        return BudgetDashboardResponseDto.builder()
            .currentMonth(viewedMonth.toString())
            .prevMonth(viewedMonth.minusMonths(1).toString())
            .nextMonth(viewedMonth.plusMonths(1).toString())
            .daysInMonth(viewedMonth.lengthOfMonth())
            .remainingDays(remainingDays)
            .totalIncome(totalIncome)
            .totalFixedCost(totalFixedCost)
            .totalGeneralExpense(totalGeneralExpense)
            .totalExpense(totalExpense)
            .currentBalance(currentBalance)
            .dailyBudget(dailyBudget)
            .budgetStatus(status)
            .message(message)
            .build();
    }
}