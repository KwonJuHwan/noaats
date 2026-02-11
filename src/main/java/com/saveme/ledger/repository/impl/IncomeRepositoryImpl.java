package com.saveme.ledger.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saveme.ledger.repository.custom.IncomeRepositoryCustom;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

import static com.saveme.ledger.domain.QIncome.income;

@RequiredArgsConstructor
public class IncomeRepositoryImpl implements IncomeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long sumAmountByMemberIdAndDateBetween(Long memberId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
            .select(income.amount.sum().coalesce(0L))
            .from(income)
            .where(
                income.member.id.eq(memberId),
                income.date.between(startDate, endDate)
            )
            .fetchOne();
    }
}