package com.saveme.ledger.repository.custom;

import com.saveme.ledger.domain.Expense;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepositoryCustom {
    // 특정 월의 지출 내역 전체 조회
    List<Expense> findExpensesByYearMonth(Long memberId, LocalDate yearMonth);
    // 특정 일자 상세 조회
    List<Expense> findExpensesByDate(Long memberId, LocalDate date);

    Long sumAmountByMemberIdAndDateBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);
}