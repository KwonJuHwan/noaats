package com.saveme.ledger.service.query;



import com.saveme.ledger.dto.response.IncomeResponseDto;
import com.saveme.ledger.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncomeQueryService {

    private final IncomeRepository incomeRepository;

    public List<IncomeResponseDto> getAllIncomes(Long memberId) {
        return incomeRepository.findAllByMemberIdOrderByDateDesc(memberId).stream()
            .map(IncomeResponseDto::new)
            .toList();
    }

    public Long getMonthlyIncomeTotal(Long memberId, LocalDate date) {
        LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());
        return incomeRepository.sumAmountByMemberIdAndDateBetween(memberId, start, end);
    }
}