package com.saveme.consumption.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record InventoryUpdateDto(
    @NotBlank(message = "재료명을 입력해주세요.") String name,
    @NotNull(message = "유통기한을 입력해주세요.") LocalDate expiryDate
) {}