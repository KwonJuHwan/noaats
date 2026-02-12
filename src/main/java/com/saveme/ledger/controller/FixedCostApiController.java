package com.saveme.ledger.controller;

import com.saveme.ledger.dto.request.FixedCostRequestDto;
import com.saveme.ledger.service.command.FixedCostCommandService;
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
@RequestMapping("/api/fixed-costs")
@RequiredArgsConstructor
public class FixedCostApiController {

    private final FixedCostCommandService fixedCostCommandService;
    private static final Long MEMBER_ID = 1L;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody FixedCostRequestDto request) {
        Long id = fixedCostCommandService.registerFixedCost(MEMBER_ID, request);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody FixedCostRequestDto request) {
        fixedCostCommandService.updateFixedCost(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fixedCostCommandService.deleteFixedCost(id);
        return ResponseEntity.ok().build();
    }
}