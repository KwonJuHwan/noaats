package com.saveme.ledger.domain;


import com.saveme.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private LocalDateTime spentAt; // 지출 일시

    @Column(length = 100)
    private String memo;

    @Column(nullable = false)
    private Boolean isFixed; // 고정지출 여부

    @Column(nullable = false)
    private Boolean isImpulse; // 충동구매 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public Expense(Member member, Category category, Long amount, LocalDateTime spentAt, String memo, Boolean isFixed) {
        this.member = member;
        this.category = category;
        this.amount = amount;
        this.spentAt = spentAt;
        this.memo = memo;
        this.isFixed = (isFixed != null) ? isFixed : false;
        this.isImpulse = false;
    }

    public void markAsImpulse() {
        this.isImpulse = true;
    }

    public void modifyExpense(Long amount, String memo, Category category) {
        this.amount = amount;
        this.memo = memo;
        this.category = category;
    }
}