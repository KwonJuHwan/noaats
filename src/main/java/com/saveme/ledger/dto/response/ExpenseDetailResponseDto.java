package com.saveme.ledger.dto.response;

import com.saveme.ledger.domain.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class ExpenseDetailResponseDto {
    private Long expenseId;
    private Long amount;
    private String memo;
    private String spentTime;
    private Long categoryId;
    private String categoryName;
    private Boolean isFixed;

    public static ExpenseDetailResponseDto from(Expense expense) {
        return ExpenseDetailResponseDto.builder()
            .expenseId(expense.getId())
            .amount(expense.getAmount())
            .memo(expense.getMemo())
            .spentTime(expense.getSpentAt().format(DateTimeFormatter.ofPattern("HH:mm")))
            .categoryId(expense.getCategory().getId())
            .categoryName(expense.getCategory().getName())
            .isFixed(expense.getIsFixed())
            .build();
    }
}