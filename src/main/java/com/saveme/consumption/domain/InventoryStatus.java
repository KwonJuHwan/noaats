package com.saveme.consumption.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InventoryStatus {
    IN_STORE("보관중"),
    CONSUMED("소진"),
    DISCARDED("폐기"),
    EXPIRED("만료됨");

    private final String description;
}