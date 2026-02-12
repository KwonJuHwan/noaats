package com.saveme.ledger.dto.response;

import java.util.List;

public record StatisticsViewResponseDto(
    String currentMonth,
    String prevMonth,
    String nextMonth,
    List<CategoryStatisticResponseDto> stats,
    boolean hasData
) {
    public static StatisticsViewResponseDto of(String current, String prev, String next, List<CategoryStatisticResponseDto> stats) {
        return new StatisticsViewResponseDto(current, prev, next, stats, !stats.isEmpty());
    }
}