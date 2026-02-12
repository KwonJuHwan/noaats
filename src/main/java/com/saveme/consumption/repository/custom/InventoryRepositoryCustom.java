package com.saveme.consumption.repository.custom;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.domain.InventoryStatus;
import java.time.LocalDate;
import java.util.List;

public interface InventoryRepositoryCustom {

    List<Inventory> findInStoreItems(Long memberId);
    boolean existsByMemberIdAndStatusAndExpiryDateBetween(Long memberId, InventoryStatus status, LocalDate start, LocalDate end);
    List<Inventory> findExpiringIngredients(Long memberId, InventoryStatus status, LocalDate endDate);
    List<Inventory> findAllByStatusAndExpiryDateBefore(InventoryStatus status, LocalDate referenceDate);
}

