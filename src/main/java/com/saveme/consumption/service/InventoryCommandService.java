package com.saveme.consumption.service;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.domain.InventoryStatus;
import com.saveme.consumption.dto.request.InventoryUpdateDto;
import com.saveme.consumption.repository.InventoryRepository;
import com.saveme.global.error.ErrorCode;
import com.saveme.global.error.exception.BusinessException;
import com.saveme.ledger.domain.Expense;
import com.saveme.member.domain.Member;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryCommandService {

    private final InventoryRepository inventoryRepository;

    public Long createInventory(Member member, String name, LocalDate expiryDate, Expense expense) {

        Inventory inventory = Inventory.builder()
            .member(member)
            .expense(expense)
            .name(name)
            .purchaseDate(expense.getSpentAt().toLocalDate())
            .expiryDate(expiryDate)
            .build();

        return inventoryRepository.save(inventory).getId();
    }

    public void updateInventory(Long inventoryId, InventoryUpdateDto dto) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_NOT_FOUND));
        inventory.updateInfo(dto.name(), dto.expiryDate());
    }

    public void changeStatus(Long inventoryId, InventoryStatus status) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_NOT_FOUND));
        inventory.changeStatus(status);
    }

    public void deleteInventory(Long inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }
}