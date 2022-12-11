package com.encore.byebuying.domain.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
/**
 * 주문 상품 id
 * 상품 이름
 * 상품 가격
 * 주문 수량
 * 주문 가격 (주문 수량 * 상품 가격)
 */
@Data
public class OrderItemListVO {
    @JsonIgnore
    private Long orderId;
    private String itemName;
    private int itemPrice;
    private int count;
    private int orderPrice;

    @QueryProjection
    public OrderItemListVO(Long orderId, String itemName, int itemPrice, int count, int orderPrice) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.orderPrice = orderPrice;
    }
}
