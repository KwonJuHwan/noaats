package com.saveme.ledger.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BudgetDashboardResponseDto {
    // 날짜 관련
    private String currentMonth;
    private String prevMonth;
    private String nextMonth;
    private int daysInMonth;
    private long remainingDays;

    // 금액 관련
    private Long totalIncome;
    private Long totalFixedCost;
    private Long totalGeneralExpense;
    private Long totalExpense; // 고정 + 일반
    private Long currentBalance; // 현재 잔액

    //생존 예산 관련
    private Long dailyBudget;
    private String budgetStatus;
    private String message;

    // 냉장고 버튼 트리거
    private boolean hasExpiringIngredients;
}