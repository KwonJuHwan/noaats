package com.saveme.ledger.service.command;

import com.saveme.ledger.domain.Income;
import com.saveme.ledger.dto.request.IncomeRequestDto;
import com.saveme.ledger.repository.IncomeRepository;
import com.saveme.member.domain.Member;
import com.saveme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IncomeCommandService {

    private final IncomeRepository incomeRepository;
    private final MemberRepository memberRepository;

    public Long createIncome(Long memberId, IncomeRequestDto request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Income income = Income.builder()
            .member(member)
            .amount(request.getAmount())
            .incomeType(request.getIncomeType())
            .date(request.getDate())
            .isRegular(request.getIsRegular())
            .build();

        return incomeRepository.save(income).getId();
    }

    public void updateIncome(Long incomeId, IncomeRequestDto request) {
        Income income = incomeRepository.findById(incomeId)
            .orElseThrow(() -> new IllegalArgumentException("수입 내역이 없습니다."));

        income.update(request.getAmount(), request.getIncomeType(), request.getDate(), request.getIsRegular());
    }

    public void deleteIncome(Long incomeId) {
        incomeRepository.deleteById(incomeId);
    }
}