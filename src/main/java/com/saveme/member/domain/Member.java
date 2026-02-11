package com.saveme.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Long defaultIncome; // 기본 월급

    @Column(nullable = false, length = 50)
    private String timezone;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Member(String name, Long defaultIncome, String timezone) {
        this.name = name;
        this.defaultIncome = (defaultIncome != null) ? defaultIncome : 0L;
        this.timezone = (timezone != null) ? timezone : "Asia/Seoul";
    }


    public void updateIncome(Long defaultIncome) {
        if (defaultIncome != null) this.defaultIncome = defaultIncome;
    }
}
