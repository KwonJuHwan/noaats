package com.saveme.ledger.repository;

import com.saveme.ledger.domain.Expense;
import com.saveme.ledger.repository.custom.ExpenseRepositoryCustom;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryCustom {

    default Long sumAmountByMemberIdAndDate(Long memberId, LocalDate date) {
        return sumAmountByMemberIdAndDateBetween(
            memberId,
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX)
        );
    }
}
