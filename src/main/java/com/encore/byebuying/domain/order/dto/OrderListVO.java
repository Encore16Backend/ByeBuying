package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.OrderItem;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderListVO {
    /**
     *  주문 상품 목록
     *  배송지 주소
     *  주문일자
     *  주문상태
     */
    private List<OrderItemResponseVO> items;
    private Address address;
    private LocalDateTime orderDate;
    private String orderState;

    public OrderListVO(Order order) {
        this.items = orderItemVOToList(order.getOrderItems());
        this.address = order.getAddress();
        this.orderDate = order.getCreatedAt();
        this.orderState = order.getOrderType().getStatus();
    }

    public static Page<OrderListVO> toPageOrderListVO(Page<Order> pageOrder) {
        return pageOrder.map(OrderListVO::new);
    }

    private List<OrderItemResponseVO> orderItemVOToList(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItemResponseVO::new).collect(Collectors.toList());
    }
}
