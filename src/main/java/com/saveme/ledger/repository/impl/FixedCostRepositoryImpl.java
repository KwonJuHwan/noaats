package com.saveme.ledger.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saveme.ledger.repository.custom.FixedCostRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.saveme.ledger.domain.QFixedCost.fixedCost;

@RequiredArgsConstructor
public class FixedCostRepositoryImpl implements FixedCostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long sumAmountByMemberId(Long memberId) {
        return queryFactory
            .select(fixedCost.amount.sum().coalesce(0L))
            .from(fixedCost)
            .where(fixedCost.member.id.eq(memberId))
            .fetchOne();
    }
}