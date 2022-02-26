package com.encore.byebuying.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class Item {
    @Id
    //@GeneratedValue(strategy = IDENTITY)
    @GeneratedValue(strategy = AUTO)
    private Long itemid; // PK
    private String itemname;
    // EAGER : 연관 관계에 있는 Entity 들 모두 가져온다 → Eager 전략
    // 관 관계에 있는 Entity 가져오지 않고, getter 로 접근할 때 가져온다 → Lazy 전략
    @OneToMany(cascade = CascadeType.ALL, fetch = LAZY)
    private Collection<Image> images = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = LAZY)
    private Collection<Category> categories = new ArrayList<>();

    private int price;
    private int purchasecnt; // 판매 수량
    private int count; // 상품 수량
    private double reviewmean; // 리뷰 평점
//    private String description; // 상품 설명
    
    private int reviewcount; // 리뷰 갯수
}
