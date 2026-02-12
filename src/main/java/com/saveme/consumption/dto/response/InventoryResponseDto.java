package com.saveme.consumption.dto.response;

import com.saveme.consumption.domain.Inventory;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponseDto {
    private Long id;
    private String name;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private String status;
    private long daysUntilExpiry;
    private String dDayMessage;

    public static InventoryResponseDto from(Inventory entity) {
        long dDay = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), entity.getExpiryDate());
        String msg = (dDay < 0) ? "만료됨" : (dDay == 0 ? "D-Day" : "D-" + dDay);

        return InventoryResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .purchaseDate(entity.getPurchaseDate())
            .expiryDate(entity.getExpiryDate())
            .status(entity.getStatus().getDescription())
            .daysUntilExpiry(dDay)
            .dDayMessage(msg)
            .build();
    }
}
