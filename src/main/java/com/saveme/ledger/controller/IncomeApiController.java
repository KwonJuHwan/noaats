package com.saveme.ledger.controller;

import com.saveme.ledger.dto.request.IncomeRequestDto;
import com.saveme.ledger.service.command.IncomeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeApiController {

    private final IncomeCommandService incomeCommandService;
    private static final Long MEMBER_ID = 1L;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody IncomeRequestDto request) {
        Long id = incomeCommandService.createIncome(MEMBER_ID, request);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody IncomeRequestDto request) {
        incomeCommandService.updateIncome(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incomeCommandService.deleteIncome(id);
        return ResponseEntity.ok().build();
    }
}