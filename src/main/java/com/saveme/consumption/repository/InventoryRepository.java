package com.saveme.consumption.repository;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.repository.custom.InventoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>,
    InventoryRepositoryCustom {}