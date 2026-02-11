package com.saveme.ledger.repository;

import com.saveme.ledger.domain.Expense;
import com.saveme.ledger.repository.custom.ExpenseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryCustom {
}
