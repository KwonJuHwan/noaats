package com.saveme.ledger.controller;

import com.saveme.ledger.dto.request.ExpenseRequestDto;
import com.saveme.ledger.dto.request.FixedCostRequestDto;
import com.saveme.ledger.dto.response.ExpenseDetailResponseDto;
import com.saveme.ledger.repository.CategoryRepository;
import com.saveme.ledger.service.command.FixedCostCommandService;
import com.saveme.ledger.service.process.ExpenseProcessService;
import com.saveme.ledger.service.query.CategoryQueryService;
import com.saveme.ledger.service.query.ExpenseQueryService;
import com.saveme.ledger.service.query.FixedCostQueryService;
import com.saveme.ledger.service.query.IncomeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LedgerController {

    private final IncomeQueryService incomeQueryService;
    private final ExpenseQueryService expenseQueryService;
    private final FixedCostQueryService fixedCostQueryService;
    private final CategoryQueryService categoryQueryService;

    private static final Long MEMBER_ID = 1L;

    @GetMapping
    public String mainDashboard(@RequestParam(required = false) String yearMonth, Model model) {
        YearMonth viewedMonth = (yearMonth == null) ? YearMonth.now() : YearMonth.parse(yearMonth);

        String currentMonthStr = viewedMonth.toString();
        LocalDate firstDayOfMonth = viewedMonth.atDay(1);

        Long totalIncome = incomeQueryService.getMonthlyIncomeTotal(MEMBER_ID, firstDayOfMonth);
        Long totalFixedCost = fixedCostQueryService.getTotalFixedCost(MEMBER_ID);
        Long totalGeneralExpense = expenseQueryService.getMonthlyExpenseTotal(MEMBER_ID, firstDayOfMonth);
        Long totalExpense = totalFixedCost + totalGeneralExpense;

        model.addAttribute("currentMonth", currentMonthStr);
        model.addAttribute("prevMonth", viewedMonth.minusMonths(1).toString());
        model.addAttribute("nextMonth", viewedMonth.plusMonths(1).toString());
        model.addAttribute("daysInMonth", viewedMonth.lengthOfMonth());
        model.addAttribute("expenseMap", expenseQueryService.getMonthlyExpenseMap(MEMBER_ID, currentMonthStr));
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalFixedCost", totalFixedCost);
        model.addAttribute("totalGeneralExpense", totalGeneralExpense);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", totalIncome - totalExpense);
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