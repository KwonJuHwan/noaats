package com.saveme.ledger.controller;

import com.saveme.ledger.domain.Income;
import com.saveme.ledger.dto.request.IncomeRequestDto;
import com.saveme.ledger.service.command.IncomeCommandService;
import com.saveme.ledger.service.query.IncomeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeCommandService incomeCommandService;
    private final IncomeQueryService incomeQueryService;
    private final Long MEMBER_ID = 1L;

    @GetMapping
    public String incomeList(Model model) {
        model.addAttribute("incomes", incomeQueryService.getAllIncomes(MEMBER_ID));
        model.addAttribute("incomeTypes", Income.IncomeType.values());
        return "ledger/incomes";
    }

    @PostMapping("/register")
    public String register(IncomeRequestDto request) {
        incomeCommandService.createIncome(MEMBER_ID, request);
        return "redirect:/incomes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        incomeCommandService.deleteIncome(id);
        return "redirect:/incomes";
    }

    @PostMapping("/update/{id}")
    public String updateFixedCost(@PathVariable Long id, @ModelAttribute IncomeRequestDto request) {
        incomeCommandService.updateIncome(id, request);
        return "redirect:/incomes";
    }
}