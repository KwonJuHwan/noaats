package com.saveme.ledger.domain;


import com.saveme.member.domain.Member;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"member_id", "year_month"})
})
public class MonthlyBudget {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;

    @Column(name = "year_month", nullable = false)
    private LocalDate yearMonth;

    @Column(nullable = false)
    private Long totalIncome;

    @Column(nullable = false)
    private Integer startDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public MonthlyBudget(Member member, LocalDate yearMonth, Long totalIncome, Integer startDay) {
        if (startDay < 1 || startDay > 31) {
            throw new IllegalArgumentException("예산 시작일은 1일에서 31일 사이여야 합니다.");
        }
        this.member = member;
        this.yearMonth = yearMonth.withDayOfMonth(1);
        this.totalIncome = totalIncome;
        this.startDay = startDay;
    }

    public void adjustTotalIncome(Long incomeAmount) {
        this.totalIncome += incomeAmount;
    }

    public int lengthOfMonth() {
        return this.yearMonth.lengthOfMonth();
    }
}
