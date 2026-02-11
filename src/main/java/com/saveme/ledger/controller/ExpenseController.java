package com.saveme.ledger.controller;

import com.saveme.ledger.dto.request.ExpenseRequestDto;
import com.saveme.ledger.service.process.ExpenseProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseProcessService expenseProcessService;

    private static final Long MEMBER_ID = 1L;

    @PostMapping("/register")
    public String registerExpense(@ModelAttribute ExpenseRequestDto request) {
        expenseProcessService.registerExpense(MEMBER_ID, request);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute ExpenseRequestDto request) {
        expenseProcessService.updateExpense(id, request);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseProcessService.deleteExpense(id);
        return "redirect:/";
    }
}
