package com.saveme.ledger.controller;

import com.saveme.ledger.dto.response.CategoryDto;
import com.saveme.ledger.service.query.CategoryQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryQueryService categoryQueryService;

    // 대분류 선택 시 소분류 조회
    @GetMapping("/{parentId}")
    public List<CategoryDto> getChildCategories(@PathVariable Long parentId) {
        return categoryQueryService.getChildCategories(parentId);
    }
}