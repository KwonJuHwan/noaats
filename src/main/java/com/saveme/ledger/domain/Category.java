package com.saveme.ledger.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    // 계층형 구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isFixedCandidate;

    @Builder
    public Category(String name, Category parent, Boolean isFixedCandidate) {
        this.name = name;
        this.parent = parent;
        this.isFixedCandidate = (isFixedCandidate != null) && isFixedCandidate;
    }
    public boolean isRoot() {
        return this.parent == null;
    }
    public boolean isGroceryContext() {
        return "식비".equals(this.name) || "장보기".equals(this.name);
    }


    // 연관관게 매핑
    public void addChildCategory(Category child) {
        this.children.add(child);
        child.parent = this;
    }
}
