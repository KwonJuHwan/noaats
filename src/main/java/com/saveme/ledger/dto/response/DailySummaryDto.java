package com.saveme.ledger.dto.response;

import com.saveme.ledger.domain.Expense;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public record DailySummaryDto(
    LocalDate date,
    Long dailyTotal,
    String topCategoryName, // 가장 돈을 많이 쓴 카테고리
    Integer expenseCount    // 지출 건수
) {
    public static DailySummaryDto from(List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) {
            return null;
        }
        LocalDate date = expenses.get(0).getSpentAt().toLocalDate();

        long totalAmount = expenses.stream()
            .mapToLong(Expense::getAmount)
            .sum();

        String topCategory = expenses.stream()
            .collect(Collectors.groupingBy(
                e -> e.getCategory().getName(),
                Collectors.summingLong(Expense::getAmount)
            ))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("기타");

        return new DailySummaryDto(
            date,
            totalAmount,
            topCategory,
            expenses.size()
        );
    }
}