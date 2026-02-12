package com.saveme.ledger.dto.request;

import java.time.LocalDate;


public record ExpenseSimulationRequestDto(
    Long amount,
    LocalDate date,
    Long categoryId
) {}
