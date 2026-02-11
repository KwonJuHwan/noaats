package com.saveme.ledger.dto.response;

import com.saveme.ledger.domain.Category;
import lombok.Getter;

@Getter
public class CategoryDto {
    private Long id;
    private String name;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public static String getFullName(Category category) {
        if (category.getParent() != null) {
            return category.getParent().getName() + " > " + category.getName();
        }
        return category.getName();
    }
}