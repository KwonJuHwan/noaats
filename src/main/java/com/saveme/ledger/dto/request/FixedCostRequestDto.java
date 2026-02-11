package com.saveme.ledger.dto.request;


import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedCostRequestDto {
    @NotNull @Positive
    private Long amount;
    @NotNull @Min(1) @Max(31)
    private Integer paymentDay;
    @NotNull
    private Long categoryId;
    @NotBlank
    private String title;
}