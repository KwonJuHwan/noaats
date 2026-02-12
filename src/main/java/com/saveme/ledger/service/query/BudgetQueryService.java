package com.saveme.ledger.service.query;

import com.saveme.consumption.domain.InventoryStatus;
import com.saveme.consumption.repository.InventoryRepository;
import com.saveme.ledger.service.logic.BudgetCalculator;
import com.saveme.ledger.domain.enums.BudgetLifecycleStatus;
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
    private final InventoryRepository inventoryRepository;
    private final BudgetCalculator calculator;

    public BudgetDashboardResponseDto getDashboardData(Long memberId, String yearMonthStr) {
        YearMonth viewedMonth = (yearMonthStr == null) ? YearMonth.now() : YearMonth.parse(yearMonthStr);
        YearMonth currentMonth = YearMonth.now();
        LocalDate today = LocalDate.now();

        // 기초 데이터 집계
        Long totalIncome = incomeQueryService.getMonthlyIncomeTotal(memberId, viewedMonth.atDay(1));
        Long totalFixedCost = fixedCostQueryService.getTotalFixedCost(memberId);
        Long totalGeneralExpense = expenseQueryService.getMonthlyExpenseTotal(memberId, viewedMonth.atDay(1));
        Long totalExpense = totalFixedCost + totalGeneralExpense;
        Long currentBalance = totalIncome - totalExpense;

        // 상태 및 예산 계산
        BudgetLifecycleStatus status = determineStatus(viewedMonth, currentMonth, currentBalance, today, memberId);
        long remainingDays = calculator.calculateRemainingDays(today, viewedMonth);
        long dailyBudget = calculateDisplayBudget(status, currentBalance, memberId, today, remainingDays);

        boolean hasExpiring = checkExpiringIngredients(memberId);

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
            .budgetStatus(status.getCode())
            .message(status.getDefaultMessage())
            .hasExpiringIngredients(hasExpiring)
            .build();
    }

    // 생존 예산 상태 결정
    private BudgetLifecycleStatus determineStatus(YearMonth viewed, YearMonth current, Long balance, LocalDate today, Long memberId) {
        if (viewed.isBefore(current)) return BudgetLifecycleStatus.PAST_MONTH;
        if (viewed.isAfter(current)) return BudgetLifecycleStatus.FUTURE_MONTH;
        if (balance < 0) return BudgetLifecycleStatus.DEFICIT;

        long remainingDays = calculator.calculateRemainingDays(today, viewed);
        if (remainingDays <= 1) return BudgetLifecycleStatus.END_OF_MONTH;

        Long todayExpense = expenseRepository.sumAmountByMemberIdAndDate(memberId, today);
        long realTimeBudget = calculator.calculateRealTimeDailyBudget(balance, todayExpense, remainingDays);

        return realTimeBudget < 0 ? BudgetLifecycleStatus.DANGER : BudgetLifecycleStatus.NORMAL;
    }

    // 생존 예산 계산
    private long calculateDisplayBudget(BudgetLifecycleStatus status, Long balance, Long memberId, LocalDate today, long remainingDays) {
        if (status == BudgetLifecycleStatus.PAST_MONTH || status == BudgetLifecycleStatus.FUTURE_MONTH) {
            return 0L;
        }
        if (status == BudgetLifecycleStatus.DEFICIT || status == BudgetLifecycleStatus.END_OF_MONTH) {
            return balance;
        }

        Long todayExpense = expenseRepository.sumAmountByMemberIdAndDate(memberId, today);
        return calculator.calculateRealTimeDailyBudget(balance, todayExpense, remainingDays);
    }

    private boolean checkExpiringIngredients(Long memberId) {
        return inventoryRepository.existsByMemberIdAndStatusAndExpiryDateBetween(
            memberId, InventoryStatus.IN_STORE, LocalDate.now().minusDays(100), LocalDate.now().plusDays(3));
    }
}