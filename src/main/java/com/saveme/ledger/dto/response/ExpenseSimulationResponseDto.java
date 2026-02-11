package com.saveme.ledger.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpenseSimulationResponseDto {
    private boolean isWarning; // 경고 필요 여부
    private Long currentDailyBudget; // 현재 하루 예산
    private Long futureDailyBudget; // 지출 후 하루 예산
    private Long budgetReduction; // 깎이는 금액
    private String message; // 경고 메시지
}