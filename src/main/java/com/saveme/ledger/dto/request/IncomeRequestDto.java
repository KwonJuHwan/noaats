package com.saveme.ledger.dto.request;

import com.saveme.ledger.domain.Income.IncomeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class IncomeRequestDto {
    private String title;
    private Long amount;
    private IncomeType incomeType;
    private LocalDate date;
    private Boolean isRegular;
}