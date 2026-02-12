package com.saveme.consumption.controller;

import com.saveme.consumption.domain.InventoryStatus;
import com.saveme.consumption.dto.request.InventoryUpdateDto;
import com.saveme.consumption.dto.response.InventoryResponseDto;
import com.saveme.consumption.service.InventoryCommandService;
import com.saveme.consumption.service.InventoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryApiController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;

    @GetMapping("/{id}")
    public InventoryResponseDto getDetail(@PathVariable Long id) {
        return inventoryQueryService.getInventoryDetail(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInfo(@PathVariable Long id, @Valid @RequestBody InventoryUpdateDto dto) {
        inventoryCommandService.updateInventory(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam InventoryStatus status) {
        inventoryCommandService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryCommandService.deleteInventory(id);
        return ResponseEntity.ok().build();
    }
}