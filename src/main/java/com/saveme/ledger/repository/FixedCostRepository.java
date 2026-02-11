package com.saveme.ledger.repository;

import com.saveme.ledger.domain.FixedCost;
import com.saveme.ledger.repository.custom.FixedCostRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedCostRepository extends JpaRepository<FixedCost, Long>,
    FixedCostRepositoryCustom {
    List<FixedCost> findByMemberId(Long memberId);

}
