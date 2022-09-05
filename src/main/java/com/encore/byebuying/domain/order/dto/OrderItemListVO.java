package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.order.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderItemListVO {
    /**
     * 주문 상품 id
     * 상품 이름
     * 상품 가격
     * 주문 수량
     * 주문 가격 (주문 수량 * 상품 가격)
     */
    @JsonIgnore
    private Long orderId;
    private String itemName;
    private int itemPrice;
    private int count;
    private int orderPrice;

    public OrderItemListVO(OrderItem orderItem) {
        this.orderId = orderItem.getOrder().getId();
        this.itemName = orderItem.getItem().getName();
        this.itemPrice = orderItem.getItem().getPrice();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
    }
}
