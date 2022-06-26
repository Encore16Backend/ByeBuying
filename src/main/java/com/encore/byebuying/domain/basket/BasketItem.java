package com.encore.byebuying.domain.basket;

import com.encore.byebuying.domain.inquiry.dto.InquirySaveDTO;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import lombok.*;

import javax.persistence.*;

//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protect
@Getter // all args constructor method에 붙여서
@ToString
public class BasketItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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


    @Builder(builderClassName = "create", builderMethodName = "createBasketItem")
    private BasketItem(Item item, int count) {
        this.count = count;
        this.item = item;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public void setCount(int count){
        this.count = count;
    }
}