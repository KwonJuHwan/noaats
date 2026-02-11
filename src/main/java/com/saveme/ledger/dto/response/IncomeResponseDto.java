package com.saveme.ledger.dto.response;

import com.saveme.ledger.domain.Income;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class IncomeResponseDto {
    private Long id;
    private Long amount;
    private String incomeType;
    private String incomeTypeKey;
    private LocalDate date;
    private Boolean isRegular;

    public IncomeResponseDto(Income income) {
        this.id = income.getId();
        this.amount = income.getAmount();
        this.incomeType = income.getIncomeType().getDescription();
        this.incomeTypeKey = income.getIncomeType().name();
        this.date = income.getDate();
        this.isRegular = income.getIsRegular();
    }
}