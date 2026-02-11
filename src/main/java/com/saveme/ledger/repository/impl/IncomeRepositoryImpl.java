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
        // 1. 비정기 수입
        Long irregularSum = queryFactory
            .select(income.amount.sum().coalesce(0L))
            .from(income)
            .where(
                income.member.id.eq(memberId),
                income.isRegular.isFalse(),
                income.date.between(startDate, endDate)
            )
            .fetchOne();

        // 2. 정기 수입
        Long regularSum = queryFactory
            .select(income.amount.sum().coalesce(0L))
            .from(income)
            .where(
                income.member.id.eq(memberId),
                income.isRegular.isTrue()
            )
            .fetchOne();

        // 3. 두 합계 더하기
        return irregularSum + regularSum;
    }
}