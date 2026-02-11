package com.saveme.ledger.service.command;


import com.saveme.ledger.domain.Category;
import com.saveme.ledger.domain.FixedCost;
import com.saveme.ledger.dto.request.FixedCostRequestDto;
import com.saveme.ledger.repository.CategoryRepository;
import com.saveme.ledger.repository.FixedCostRepository;
import com.saveme.member.domain.Member;
import com.saveme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FixedCostCommandService {

    private final FixedCostRepository fixedCostRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public Long registerFixedCost(Long memberId, FixedCostRequestDto request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        validatePaymentDay(request.getPaymentDay());

        FixedCost fixedCost = FixedCost.builder()
            .member(member)
            .category(category)
            .amount(request.getAmount())
            .paymentDay(request.getPaymentDay())
            .title(request.getTitle())
            .build();

        return fixedCostRepository.save(fixedCost).getId();
    }

    public void updateFixedCost(Long fixedCostId, FixedCostRequestDto request) {
        FixedCost fixedCost = fixedCostRepository.findById(fixedCostId)
            .orElseThrow(() -> new IllegalArgumentException("고정 지출 정보를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        validatePaymentDay(request.getPaymentDay());

        fixedCost.update(request.getAmount(), request.getPaymentDay(), request.getTitle(), category);
    }

    public void deleteFixedCost(Long fixedCostId) {
        fixedCostRepository.deleteById(fixedCostId);
    }

    private void validatePaymentDay(Integer day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("결제일은 1~31일 사이여야 합니다.");
        }
    }

}