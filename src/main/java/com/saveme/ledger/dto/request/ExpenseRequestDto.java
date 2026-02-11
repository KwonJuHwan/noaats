package com.saveme.ledger.dto.request;


import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto {
    @NotNull @Positive
    private Long amount;
    @NotNull
    private LocalDateTime spentAt;
    @NotNull
    private Long categoryId;
    private String memo;

    // 식재료 정보
    private String ingredientName;
    private LocalDate expiryDate;

    public boolean hasInventoryInfo() {
        return ingredientName != null && !ingredientName.isBlank() && expiryDate != null;
    }
}