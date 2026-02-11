package com.saveme.consumption.domain;


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

    public enum Status {
        IN_FRIDGE, CONSUMED, TRASHED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Long purchasePrice;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Inventory(Member member, String name, Long purchasePrice, LocalDate expiryDate, LocalDate purchaseDate) {
        this.member = member;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.expiryDate = expiryDate;
        this.purchaseDate = purchaseDate;
        this.status = Status.IN_FRIDGE;
    }

    public void consume() {
        this.status = Status.CONSUMED;
    }

    public void trash() {
        this.status = Status.TRASHED;
    }
}