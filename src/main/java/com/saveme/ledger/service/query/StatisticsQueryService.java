package com.saveme.ledger.service.query;

import com.saveme.ledger.dto.response.CategoryStatisticResponseDto;
import com.saveme.ledger.repository.ExpenseRepository;
import com.saveme.ledger.repository.FixedCostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsQueryService {

    private final FixedCostRepository fixedCostRepository;
    private final ExpenseRepository expenseRepository;

    public List<CategoryStatisticResponseDto> getMonthlyStatistics(Long memberId, String yearMonthStr) {
        YearMonth yearMonth = (yearMonthStr == null) ? YearMonth.now() : YearMonth.parse(yearMonthStr);
        LocalDate targetDate = yearMonth.atDay(1);

        Map<String, Long> fixedCosts = fixedCostRepository.findFixedCostAmountsByCategory(memberId);
        Map<String, Long> expenses = expenseRepository.findExpenseAmountsByCategory(memberId, targetDate);

        Map<String, Long> mergedMap = new HashMap<>(fixedCosts);
        expenses.forEach((category, amount) ->
            mergedMap.merge(category, amount, Long::sum)
        );

        long totalAmount = mergedMap.values().stream().mapToLong(Long::longValue).sum();

        if (totalAmount == 0) {
            return Collections.emptyList();
        }

        return mergedMap.entrySet().stream()
            .map(entry -> {
                double percentage = (double) entry.getValue() / totalAmount * 100.0;
                return new CategoryStatisticResponseDto(
                    entry.getKey(),
                    entry.getValue(),
                    Math.round(percentage * 10) / 10.0
                );
            })
            .sorted(Comparator.comparing(CategoryStatisticResponseDto::totalAmount).reversed())
            .collect(Collectors.toList());
    }
}
