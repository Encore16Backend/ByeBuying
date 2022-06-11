package com.encore.byebuying.domain.basket;

import com.encore.byebuying.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // 장바구니에 들어있는 상품의 총 가격
//    private int price;

    // 장바구니에 담은 해당 상품의 갯수
    private int count;

    public static BasketItem createBasketItem(Item item, int count) {
        BasketItem basketItem = BasketItem.builder()
                .item(item)
                .count(count)
                .build();
        return basketItem;
    }
    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
