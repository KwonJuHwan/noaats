package com.saveme.ledger.repository.impl;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saveme.ledger.domain.Expense;
import com.saveme.ledger.repository.custom.ExpenseRepositoryCustom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.saveme.ledger.domain.QExpense.expense;
import static com.saveme.ledger.domain.QCategory.category;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Expense> findExpensesByYearMonth(Long memberId, LocalDate yearMonth) {
        LocalDate startOfMonth = yearMonth.withDayOfMonth(1);
        LocalDate endOfMonth = yearMonth.withDayOfMonth(yearMonth.lengthOfMonth());

        return queryFactory
            .selectFrom(expense)
            .join(expense.category, category).fetchJoin()
            .where(
                expense.member.id.eq(memberId),
                expense.spentAt.between(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59))
            )
            .orderBy(expense.spentAt.desc())
            .fetch();
    }

    @Override
    public List<Expense> findExpensesByDate(Long memberId, LocalDate date) {
        return queryFactory
            .selectFrom(expense)
            .join(expense.category, category).fetchJoin()
            .where(
                expense.member.id.eq(memberId),
                expense.spentAt.between(
                    date.atStartOfDay(),
                    date.atTime(23, 59, 59)
                )
            )
            .orderBy(expense.spentAt.asc())
            .fetch();
    }
    @Override
    public Long sumAmountByMemberIdAndDateBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory
            .select(expense.amount.sum().coalesce(0L))
            .from(expense)
            .where(
                expense.member.id.eq(memberId),
                expense.spentAt.between(startDate, endDate)
            )
            .fetchOne();
    }

    @Override
    public Map<String, Long> findExpenseAmountsByCategory(Long memberId, LocalDate yearMonth) {
        LocalDate startOfMonth = yearMonth.withDayOfMonth(1);
        LocalDate endOfMonth = yearMonth.withDayOfMonth(yearMonth.lengthOfMonth());

        List<Tuple> result = queryFactory
            .select(
                category.parent.name.coalesce(category.name),
                expense.amount.sum().coalesce(0L)
            )
            .from(expense)
            .join(expense.category, category)
            .leftJoin(category.parent)
            .where(
                expense.member.id.eq(memberId),
                expense.spentAt.between(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59))
            )
            .groupBy(category.parent.name.coalesce(category.name))
            .fetch();

        return result.stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(0, String.class),
                tuple -> tuple.get(1, Long.class)
            ));
    }
}