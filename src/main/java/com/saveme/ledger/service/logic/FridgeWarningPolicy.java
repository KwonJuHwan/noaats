package com.saveme.ledger.service.logic;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.domain.InventoryStatus;
import com.saveme.consumption.repository.InventoryRepository;
import com.saveme.ledger.domain.Category;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FridgeWarningPolicy {

    private final InventoryRepository inventoryRepository;
    private final Map<String, Boolean> dailyWarningCache = new ConcurrentHashMap<>();

    public String checkFridgeWarning(Long memberId, Category category) {
        if (!isFoodButNotGrocery(category)) {
            return "";
        }

        String cacheKey = memberId + ":" + LocalDate.now();
        if (dailyWarningCache.containsKey(cacheKey)) {
            return "";
        }

        LocalDate threeDaysLater = LocalDate.now().plusDays(3);
        List<Inventory> expiringItems = inventoryRepository.findExpiringIngredients(
            memberId, InventoryStatus.IN_STORE, threeDaysLater);

        if (expiringItems.isEmpty()) {
            return "";
        }

        dailyWarningCache.put(cacheKey, true);
        return formatItemNames(expiringItems);
    }

    private boolean isFoodButNotGrocery(Category category) {
        if (category == null) return false;
        boolean isFoodRelated = "식비".equals(category.getName()) ||
            (category.getParent() != null && "식비".equals(category.getParent().getName()));
        boolean isGrocery = "장보기".equals(category.getName());
        return isFoodRelated && !isGrocery;
    }

    private String formatItemNames(List<Inventory> items) {
        String names = items.stream()
            .limit(2)
            .map(Inventory::getName)
            .collect(Collectors.joining(", "));
        if (items.size() > 2) {
            names += " 외 " + (items.size() - 2) + "건";
        }
        return names;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void clearCache() {
        dailyWarningCache.clear();
        log.info("냉장고 경고 캐시 초기화 완료");
    }
}