package com.encore.byebuying.domain.basket;

import com.encore.byebuying.domain.common.BaseTimeEntity;
import javax.persistence.*;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Basket extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY)
    private List<BasketItem> basketItems = new ArrayList<>();


    public void addBasketItem(BasketItem basketItem) {
        basketItem.setBasket(this);
        this.basketItems.add(basketItem);
    }



}
