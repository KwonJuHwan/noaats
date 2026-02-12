package com.saveme.ledger.dto.response;

public record CategoryStatisticResponseDto(
    String categoryName,
    Long totalAmount,
    Double percentage
) {}