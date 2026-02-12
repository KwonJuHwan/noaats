package com.saveme.ledger.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BudgetLifecycleStatus {
    PAST_MONTH("PAST_MONTH", "지나간 달의 지출 내역입니다. 총 지출을 확인해보세요."),
    FUTURE_MONTH("FUTURE_MONTH", "승진하셨나요? 미래의 예산을 미리 보고 계십니다."),
    END_OF_MONTH("END_OF_MONTH", "이번 달 마지막 날입니다."),
    DEFICIT("DEFICIT", "이미 적자 상태입니다! 지출을 줄이세요."),
    DANGER("DEFICIT", "오늘 예산을 초과했습니다! 내일 예산이 줄어듭니다."),
    NORMAL("NORMAL", "오늘 사용 가능한 금액입니다.");

    private final String code;
    private final String defaultMessage;
}