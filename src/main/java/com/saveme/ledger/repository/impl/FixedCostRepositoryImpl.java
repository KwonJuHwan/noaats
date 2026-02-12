package com.saveme.ledger.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saveme.ledger.repository.custom.FixedCostRepositoryCustom;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import static com.saveme.ledger.domain.QCategory.category;
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

    @Override
    public Map<String, Long> findFixedCostAmountsByCategory(Long memberId) {
        StringPath categoryName = category.name;

        List<Tuple> result = queryFactory
            .select(
                category.parent.name.coalesce(category.name),
                fixedCost.amount.sum().coalesce(0L)
            )
            .from(fixedCost)
            .join(fixedCost.category, category)
            .leftJoin(category.parent)
            .where(fixedCost.member.id.eq(memberId))
            .groupBy(category.parent.name.coalesce(category.name))
            .fetch();

        return result.stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(0, String.class),
                tuple -> tuple.get(1, Long.class)
            ));
    }
}