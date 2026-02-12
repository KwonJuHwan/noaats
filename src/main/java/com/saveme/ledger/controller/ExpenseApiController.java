package com.saveme.ledger.controller;

import com.saveme.ledger.domain.enums.OpportunityCost;
import com.saveme.ledger.dto.request.ExpenseRequestDto;
import com.saveme.ledger.dto.request.ExpenseSimulationRequestDto;
import com.saveme.ledger.dto.response.ExpenseDetailResponseDto;
import com.saveme.ledger.dto.response.ExpenseRegistrationResponseDto;
import com.saveme.ledger.dto.response.ExpenseSimulationResponseDto;
import com.saveme.ledger.service.process.BudgetProcessService;
import com.saveme.ledger.service.process.ExpenseProcessService;
import com.saveme.ledger.service.query.ExpenseQueryService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseApiController {

    private final ExpenseProcessService expenseProcessService;
    private final ExpenseQueryService expenseQueryService;
    private final BudgetProcessService budgetProcessService;
    private static final Long MEMBER_ID = 1L;

    @PostMapping
    public ResponseEntity<ExpenseRegistrationResponseDto> registerExpense(@RequestBody ExpenseRequestDto request) {
        Long id = expenseProcessService.registerExpense(MEMBER_ID, request);
        String message = OpportunityCost.getMessage(request.getAmount());

        return ResponseEntity.ok(new ExpenseRegistrationResponseDto(id, message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExpense(@PathVariable Long id, @RequestBody ExpenseRequestDto request) {
        expenseProcessService.updateExpense(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseProcessService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pre-check")
    public ExpenseSimulationResponseDto checkBudgetImpact(@RequestBody ExpenseSimulationRequestDto request) {
        return budgetProcessService.simulateExpense(MEMBER_ID, request);
    }

    @GetMapping("/daily")
    public List<ExpenseDetailResponseDto> getDailyExpenses(@RequestParam LocalDate date) {
        return expenseQueryService.getDailyExpenses(MEMBER_ID, date);
    }
}