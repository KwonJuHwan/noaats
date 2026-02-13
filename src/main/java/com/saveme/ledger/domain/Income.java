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
public class Income {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private IncomeType incomeType;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean isRegular; // 정기 수입 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Income(Member member, Long amount, IncomeType incomeType, LocalDate date, Boolean isRegular) {
        this.member = member;
        this.amount = amount;
        this.incomeType = incomeType;
        this.date = date;
        this.isRegular = (isRegular != null) ? isRegular : false;
    }

    public void update(Long amount, IncomeType incomeType, LocalDate date, Boolean isRegular) {
        this.amount = amount;
        this.incomeType = incomeType;
        this.date= date;
        this.isRegular = isRegular;
    }

    @Getter
    public enum IncomeType {
        SALARY("월급"),
        BONUS("상여금"),
        POCKET_MONEY("용돈"),
        ETC("기타");

        private final String description;

        IncomeType(String description) {
            this.description = description;
        }
    }
}