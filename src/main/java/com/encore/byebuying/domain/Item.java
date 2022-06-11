package com.encore.byebuying.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item extends BaseTimeEntity {

    @Column(name = "item_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private String name;
    // EAGER : 연관 관계에 있는 Entity 들 모두 가져온다 → Eager 전략
    // 연관 관계에 있는 Entity 가져오지 않고, getter 로 접근할 때 가져온다 → Lazy 전략
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Image> images = new ArrayList<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Collection<Category> categories = new ArrayList<>();

    private int price;

    // 카테고리랑은 연관관계 안 잡아도 된다.
    // 상품에서 카테고리를 확인할 일이 없음.
    private Long categoryId;

    private int stockQuantity; // 상품 수량

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Inquiry> inquiries = new ArrayList<>();

    // 생성 메소드
    // 파라미터 인자는 꼭 필요한 애들
    public static Item createItem(String name, int price) {

        return Item.builder()
                .name(name)
                .price(price)
                .build();
    }

    // 비즈니스 로직

    /**
     * 카테고리 설정
     */
    public void settingCategory(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 재고 설정
     */
    public void settingStock(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /** 재고 감소 */
    public void removeStock(int count) {
        int restStock = stockQuantity - count;
        if(restStock < 0) {
            throw new RuntimeException("재고는 0보다 작아질 수 없습니다.");
        }
        stockQuantity = restStock;
    }
}