package com.saveme.ledger.controller;


import com.saveme.ledger.dto.response.BudgetDashboardResponseDto;
import com.saveme.ledger.service.query.BudgetQueryService;
import com.saveme.ledger.service.query.CategoryQueryService;
import com.saveme.ledger.service.query.ExpenseQueryService;
import com.saveme.ledger.service.query.FixedCostQueryService;
import com.saveme.ledger.service.query.IncomeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LedgerController {

    private final ExpenseQueryService expenseQueryService;
    private final CategoryQueryService categoryQueryService;
    private final BudgetQueryService budgetQueryService;

    private static final Long MEMBER_ID = 1L;

    @GetMapping
    public String mainDashboard(@RequestParam(required = false) String yearMonth, Model model) {
        BudgetDashboardResponseDto dashboardData = budgetQueryService.getDashboardData(MEMBER_ID, yearMonth);

        model.addAttribute("dashboard", dashboardData);

        model.addAttribute("expenseMap", expenseQueryService.getMonthlyExpenseMap(MEMBER_ID, dashboardData.getCurrentMonth()));
        model.addAttribute("rootCategories", categoryQueryService.getRootCategories());

        return "ledger/main";
    }

    /**
     * 현재 미구현
     */
    @GetMapping("/fridge")
    public String fridge() {
        return "ledger/fridge";
    }

    /**
     * 현재 미구현
     */
    @GetMapping("/statistics")
    public String statistics() {
        return "ledger/statistics";
    }


}