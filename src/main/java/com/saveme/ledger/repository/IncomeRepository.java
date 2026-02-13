package com.saveme.ledger.repository;

import com.saveme.ledger.domain.Income;
import com.saveme.ledger.repository.custom.IncomeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long>, IncomeRepositoryCustom {

    List<Income> findAllByMemberIdOrderByAmountDesc(Long memberId);

}