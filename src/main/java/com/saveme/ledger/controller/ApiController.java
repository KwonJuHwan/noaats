package com.saveme.ledger.controller;


import com.saveme.ledger.dto.response.CategoryDto;
import com.saveme.ledger.dto.response.ExpenseDetailResponseDto;
import com.saveme.ledger.service.query.CategoryQueryService;
import com.saveme.ledger.service.query.ExpenseQueryService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ExpenseQueryService expenseQueryService;
    private final CategoryQueryService categoryQueryService;

    private static final Long MEMBER_ID = 1L;

    @GetMapping("/expenses/daily")
    public List<ExpenseDetailResponseDto> getDailyDetail(@RequestParam String date) {
        return expenseQueryService.getDailyExpenses(MEMBER_ID, LocalDate.parse(date));
    }

    @GetMapping("/categories/{parentId}")
    public List<CategoryDto> getChildCategories(@PathVariable Long parentId) {
        return categoryQueryService.getChildCategories(parentId);
    }
}
