package com.saveme.consumption.domain;



import com.saveme.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpportunityCost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opportunity_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Long unitPrice;

    @Column(nullable = false)
    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public OpportunityCost(Member member, String name, Long unitPrice, Boolean isDefault) {
        this.member = member;
        this.name = name;
        this.unitPrice = unitPrice;
        this.isDefault = (isDefault != null) && isDefault;
    }

    public void changePrice(Long newPrice) {
        this.unitPrice = newPrice;
    }
}
