package com.saveme.consumption.service;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.dto.response.InventoryResponseDto;
import com.saveme.consumption.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryQueryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResponseDto> getMyFridge(Long memberId) {
        return inventoryRepository.findInStoreItems(memberId).stream()
            .map(InventoryResponseDto::from)
            .toList();
    }

    public InventoryResponseDto getInventoryDetail(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> new IllegalArgumentException("재료 없음"));
        return InventoryResponseDto.from(inventory);
    }
}
