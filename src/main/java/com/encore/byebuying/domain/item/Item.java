package com.encore.byebuying.domain.item;

import com.encore.byebuying.domain.category.Category;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.review.Review;
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
    private Collection<ItemImage> itemImages = new ArrayList<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Collection<Category> categories = new ArrayList<>();

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    private int stockQuantity = 0; // 상품 수량

//    Item에 Inquiry 필요 x
//    @Builder.Default
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<Inquiry> inquiries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Review> reviews = new ArrayList<>();


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
    public void setCategory(Category category) {
        this.category = category;
        category.getItems().add(this);
    }

    /**
     * 재고 설정
     */
    public void settingStock(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * 재고 증가
     */
    public void addStock(int count) {
        stockQuantity += count;
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