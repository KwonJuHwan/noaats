package com.saveme.ledger.repository.custom;

import java.time.LocalDate;

public interface IncomeRepositoryCustom {
    Long sumAmountByMemberIdAndDateBetween(Long memberId, LocalDate startDate, LocalDate endDate);
}