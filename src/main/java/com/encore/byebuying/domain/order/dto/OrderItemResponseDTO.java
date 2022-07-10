package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.item.dto.ItemResponseDTO;
import com.encore.byebuying.domain.order.OrderItem;
import lombok.Data;

@Data
public class OrderItemResponseDTO {

    private ItemResponseDTO item;
    private int orderPrice;
    private int count;

    public OrderItemResponseDTO(OrderItem orderItem) {
        this.item = new ItemResponseDTO(orderItem.getItem());
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
