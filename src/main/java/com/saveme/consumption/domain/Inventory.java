package com.saveme.consumption.domain;


import com.saveme.ledger.domain.Expense;
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
public class Inventory {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InventoryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @Builder
    public Inventory(String name, LocalDate purchaseDate, LocalDate expiryDate, Member member, Expense expense) {
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.status = InventoryStatus.IN_STORE;
        this.member = member;
        this.expense = expense;
    }

    public void updateInfo(String name, LocalDate expiryDate) {
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public void changeStatus(InventoryStatus status) {
        this.status = status;
    }
}