package com.saveme.consumption.service;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.domain.InventoryStatus;
import com.saveme.consumption.repository.InventoryRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryBatchService {

    private final InventoryRepository inventoryRepository;

    // 매일 자정 실행
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void expireItems() {
        LocalDate today = LocalDate.now();

        // 유통기한이 어제까지였고, 아직 '보관중'인 아이템 조회
        List<Inventory> expiredItems = inventoryRepository.findAllByStatusAndExpiryDateBefore(
            InventoryStatus.IN_STORE, today);

        for (Inventory item : expiredItems) {
            item.changeStatus(InventoryStatus.EXPIRED);
        }

        if (!expiredItems.isEmpty()) {
            log.info("[Batch] 총 {}개의 식재료가 만료 처리되었습니다.", expiredItems.size());
        }
    }
}