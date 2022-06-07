package com.encore.byebuying.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

//    // 널 허용 불가
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id")
//    private User user;

//    private String username;

//    private int bcount; // 개수

    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY)
    private List<BasketItem> basketItems = new ArrayList<>();

//    private Long itemid;
//    private String itemimg;
//    private String itemname;
//    private int itemprice;


    public void userAddBasket(User user) {
        user.setBasket(this);
    }

    public void addBasketItem(BasketItem basketItem) {
        basketItem.setBasket(this);
        this.basketItems.add(basketItem);
    }
    public void updateBasketItem(BasketItem updateBasketItem){
        for (BasketItem basketItem : this.basketItems){
            if (basketItem.getItem().getId() == updateBasketItem.getItem().getId()){
                basketItem = updateBasketItem;
            }
        }
    }
    // item_id로 삭제
    public void deleteBasketItem(Long item_id) {
        this.basketItems.removeIf(item -> item.getItem().getId() == item_id);
    }


}
