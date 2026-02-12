package com.saveme.consumption.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.domain.InventoryStatus;
import com.saveme.consumption.repository.custom.InventoryRepositoryCustom;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.saveme.consumption.domain.QInventory.inventory;

@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Inventory> findInStoreItems(Long memberId) {
        return queryFactory
            .selectFrom(inventory)
            .where(
                inventory.member.id.eq(memberId),
                inventory.status.eq(InventoryStatus.IN_STORE)
            )
            .orderBy(inventory.expiryDate.asc())
            .fetch();
    }

    @Override
    public boolean existsByMemberIdAndStatusAndExpiryDateBetween(Long memberId, InventoryStatus status, LocalDate start, LocalDate end) {
        Integer fetchOne = queryFactory
            .selectOne()
            .from(inventory)
            .where(
                inventory.member.id.eq(memberId),
                inventory.status.eq(status),
                inventory.expiryDate.between(start, end)
            )
            .fetchFirst();
        return fetchOne != null;
    }

    @Override
    public List<Inventory> findExpiringIngredients(Long memberId, InventoryStatus status, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        return queryFactory
            .selectFrom(inventory)
            .where(
                inventory.member.id.eq(memberId),
                inventory.status.eq(status),
                inventory.expiryDate.between(today, endDate)
            )
            .orderBy(inventory.expiryDate.asc())
            .fetch();
    }

    @Override
    public List<Inventory> findAllByStatusAndExpiryDateBefore(InventoryStatus status, LocalDate referenceDate) {
        return queryFactory
            .selectFrom(inventory)
            .where(
                inventory.status.eq(status),
                inventory.expiryDate.before(referenceDate)
            )
            .fetch();
    }
}