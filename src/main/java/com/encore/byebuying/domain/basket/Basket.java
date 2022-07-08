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
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protect
@Getter // all args constructor method에 붙여서
@ToString
public class Basket extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<BasketItem> basketItems = new ArrayList<>();

    public void addBasketItem(BasketItem basketItem) {
        basketItem.setBasket(this);
        this.basketItems.add(basketItem);
    }

    public static Basket createBasket() {
        return new Basket();
    }
}