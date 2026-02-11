package com.saveme.ledger.dto.response;

import com.saveme.ledger.domain.Category;
import com.saveme.ledger.domain.FixedCost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FixedCostResponseDto {
    private Long fixedCostId;
    private String title;
    private Long amount;
    private Integer paymentDay;
    private Long categoryId;
    private Long parentId;
    private String categoryName;

    public static FixedCostResponseDto from(FixedCost fixedCost) {
        Category category = fixedCost.getCategory();
        Long parentId = null;
        String fullName = category.getName();

        if (category.getParent() != null) {
            parentId = category.getParent().getId();
            fullName = category.getParent().getName() + " > " + category.getName();
        }

        return FixedCostResponseDto.builder()
            .fixedCostId(fixedCost.getId())
            .title(fixedCost.getTitle())
            .amount(fixedCost.getAmount())
            .paymentDay(fixedCost.getPaymentDay())
            .categoryId(category.getId())
            .parentId(parentId)
            .categoryName(fullName)
            .build();
    }
}