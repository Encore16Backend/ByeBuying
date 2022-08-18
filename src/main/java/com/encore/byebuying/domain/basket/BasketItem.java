package com.encore.byebuying.domain.basket;

import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protect
@Getter // all args constructor method에 붙여서
@ToString
@Table(indexes = @Index(columnList = "basket_id"))
public class BasketItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // 장바구니에 담은 해당 상품의 갯수
    @Column(name = "count", nullable = false)
    private int count;

    @Builder(builderClassName = "create", builderMethodName = "createBasketItem")
    private BasketItem(Item item, int count, Basket basket) {
        this.count = count;
        this.item = item;
        this.basket = basket;
    }

    public void setCount(int count){
        this.count = count;
    }
}