package com.saveme.ledger.service.query;

import com.saveme.ledger.domain.FixedCost;
import com.saveme.ledger.dto.response.FixedCostResponseDto;
import com.saveme.ledger.repository.FixedCostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FixedCostQueryService {

    private final FixedCostRepository fixedCostRepository;

    public Long getTotalFixedCost(Long memberId) {
        return fixedCostRepository.sumAmountByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public List<FixedCostResponseDto> getFixedCostsOrderByAmount(Long memberId) {
        List<FixedCost> fixedCosts = fixedCostRepository.findAllByMemberIdOrderByAmountDesc(memberId);

        return fixedCosts.stream()
            .map(FixedCostResponseDto::from)
            .toList();
    }
}