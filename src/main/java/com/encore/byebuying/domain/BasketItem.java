package com.encore.byebuying.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {

    @Id @GeneratedValue
    @Column(name = "basket_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int price;

    private int count;
}
