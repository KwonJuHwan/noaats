package com.saveme.ledger.service.query;


import com.saveme.ledger.domain.Expense;
import com.saveme.ledger.dto.response.DailySummaryDto; // DTO는 간단히 가정
import com.saveme.ledger.dto.response.ExpenseDetailResponseDto;
import com.saveme.ledger.repository.ExpenseRepository;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseQueryService {

    private final ExpenseRepository expenseRepository;

    /**
     * 월별 일자별 총 지출액 및 내역 조회
     */
    public Map<LocalDate, DailySummaryDto> getMonthlyExpenseMap(Long memberId, String yearMonthStr) {
        LocalDate yearMonth = LocalDate.parse(yearMonthStr + "-01");

        List<Expense> expenses = expenseRepository.findExpensesByYearMonth(memberId, yearMonth);

        return expenses.stream()
            .collect(Collectors.groupingBy(
                e -> e.getSpentAt().toLocalDate(),
                Collectors.collectingAndThen(Collectors.toList(), DailySummaryDto::from)
            ));
    }

    public List<ExpenseDetailResponseDto> getDailyExpenses(Long memberId, LocalDate date) {
        List<Expense> expenses = expenseRepository.findExpensesByDate(memberId, date);

        return expenses.stream()
            .map(ExpenseDetailResponseDto::from)
            .toList();
    }

    public Long getMonthlyExpenseTotal(Long memberId, LocalDate date) {
        LocalDateTime start = date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime end = date.with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        return expenseRepository.sumAmountByMemberIdAndDateBetween(memberId, start, end);
    }
}