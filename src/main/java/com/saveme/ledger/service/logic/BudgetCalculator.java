package com.saveme.ledger.service.logic;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class BudgetCalculator {

    public long calculateRemainingDays(LocalDate today, YearMonth currentMonth) {
        return ChronoUnit.DAYS.between(today, currentMonth.atEndOfMonth()) + 1;
    }

    public long calculateRealTimeDailyBudget(long currentBalance, long todayExpense, long remainingDays) {
        long balanceStartOfDay = currentBalance + todayExpense;
        if (remainingDays <= 0) return 0;
        long baseDailyBudget = balanceStartOfDay / remainingDays;
        return baseDailyBudget - todayExpense;
    }
}