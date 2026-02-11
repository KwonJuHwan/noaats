package com.saveme.ledger.domain;


import com.saveme.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedCost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fixed_cost_id")
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Integer paymentDay; // 1~31

    @Column(nullable = false, length = 50)
    private String title;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public FixedCost(Member member, Category category, Long amount, Integer paymentDay, String title) {
        if (paymentDay < 1 || paymentDay > 31) {
            throw new IllegalArgumentException("결제일은 1일에서 31일 사이여야 합니다.");
        }
        this.member = member;
        this.category = category;
        this.amount = amount;
        this.paymentDay = paymentDay;
        this.title = title;
    }

    public void update(Long amount, Integer paymentDay, String title, Category category){
        this.amount = amount;
        this.paymentDay = paymentDay;
        this.title = title;
        this.category = category;
    }

    public void terminate(LocalDate endDate) {
        this.endDate = endDate;
    }
}