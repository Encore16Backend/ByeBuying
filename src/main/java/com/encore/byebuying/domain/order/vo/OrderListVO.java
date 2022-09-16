package com.encore.byebuying.domain.order.vo;

import com.encore.byebuying.domain.common.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderListVO {
    /**
     * 주문 상품 목록
     * 배송지 주소
     * 주문일자
     * 주문상태
     */
    private Long orderId;
    private Address address;
    private LocalDateTime orderDate;
    private String orderState;
    private List<OrderItemListVO> items = new ArrayList<>();

    @QueryProjection
    public OrderListVO(Long orderId, Address address, LocalDateTime orderDate, String orderState) {
        this.orderId = orderId;
        this.address = address;
        this.orderDate = orderDate;
        this.orderState = orderState;
    }
}
