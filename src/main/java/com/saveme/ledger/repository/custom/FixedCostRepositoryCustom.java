package com.saveme.ledger.repository.custom;

import java.util.Map;

public interface FixedCostRepositoryCustom {
    Long sumAmountByMemberId(Long memberId);

    Map<String, Long> findFixedCostAmountsByCategory(Long memberId);
}