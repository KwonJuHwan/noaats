package com.saveme.ledger.controller;


import com.saveme.consumption.dto.response.InventoryResponseDto;
import com.saveme.consumption.service.InventoryQueryService;
import com.saveme.ledger.domain.Income;
import com.saveme.ledger.dto.response.BudgetDashboardResponseDto;
import com.saveme.ledger.dto.response.StatisticsViewResponseDto;
import com.saveme.ledger.service.process.StatisticsProcessService;
import com.saveme.ledger.service.query.BudgetQueryService;
import com.saveme.ledger.service.query.CategoryQueryService;
import com.saveme.ledger.service.query.ExpenseQueryService;
import com.saveme.ledger.service.query.FixedCostQueryService;
import com.saveme.ledger.service.query.IncomeQueryService;
import java.util.List;
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
    private final InventoryQueryService inventoryQueryService;
    private final FixedCostQueryService fixedCostQueryService;
    private final IncomeQueryService incomeQueryService;
    private final StatisticsProcessService statisticsProcessService;

    private static final Long MEMBER_ID = 1L;

    @GetMapping
    public String mainDashboard(@RequestParam(required = false) String yearMonth, Model model) {
        BudgetDashboardResponseDto dashboardData = budgetQueryService.getDashboardData(MEMBER_ID, yearMonth);

        model.addAttribute("dashboard", dashboardData);

        model.addAttribute("expenseMap", expenseQueryService.getMonthlyExpenseMap(MEMBER_ID, dashboardData.getCurrentMonth()));
        model.addAttribute("rootCategories", categoryQueryService.getRootCategories());

        return "ledger/main";
    }

    @GetMapping("/fixed-costs")
    public String fixedCostList(Model model) {
        model.addAttribute("fixedCosts", fixedCostQueryService.getFixedCosts(MEMBER_ID));
        model.addAttribute("rootCategories", categoryQueryService.getRootCategories());
        return "ledger/fixed-costs";
    }

    @GetMapping("/incomes")
    public String incomeList(Model model) {
        model.addAttribute("incomes", incomeQueryService.getAllIncomes(MEMBER_ID));
        model.addAttribute("incomeTypes", Income.IncomeType.values());
        return "ledger/incomes";
    }

    @GetMapping("/fridge")
    public String fridgeMain(Model model) {
        List<InventoryResponseDto> items = inventoryQueryService.getMyFridge(MEMBER_ID);
        model.addAttribute("items", items);
        return "consumption/fridge";
    }

    @GetMapping("/statistics")
    public String viewStatistics(
        @RequestParam(required = false) String yearMonth,
        Model model
    ) {
        StatisticsViewResponseDto view = statisticsProcessService.getStatisticsView(MEMBER_ID, yearMonth);
        model.addAttribute("view", view);

        return "ledger/statistics";
    }


}