package com.saveme.ledger.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OpportunityCost {

    BASIC(2_000L, 0L, "", ""),
    COFFEE(10_000L, 2_000L, "아메리카노", "잔"),
    GUKBAP(50_000L, 9_000L, "뜨끈한 국밥", "그릇"),
    CLOTHES(300_000L, 50_000L, "나이키 티셔츠", "벌"),
    AIRPODS(Long.MAX_VALUE, 300_000L, "에어팟 프로", "개");

    private final Long threshold;
    private final Long unitPrice;
    private final String itemName;
    private final String unitName;

    public static String getMessage(Long amount) {
        OpportunityCost cost = Arrays.stream(values())
            .filter(c -> amount <= c.threshold)
            .findFirst()
            .orElse(AIRPODS);

        if (cost == BASIC) {
            return "지출이 정상적으로 등록되었습니다.";
        }

        // 기회비용 계산
        long count = Math.round((double) amount / cost.unitPrice);
        if (count == 0) count = 1;

        return String.format("지출 등록 완료! 💸\n(이 돈이면 %s %d%s을(를) 살 수 있었어요!)",
            cost.itemName, count, cost.unitName);
    }
}