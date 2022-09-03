package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.dto.ItemRequestDTO;
import com.encore.byebuying.domain.item.dto.ItemResponseDTO;
import com.encore.byebuying.domain.order.OrderItem;
import lombok.Data;

@Data
public class OrderItemListVO {
    /**
     *  주문 상품 id
     *  상품 정보 (상품 id, 이름, 가격)
     *  주문 수량
     *  주문 가격
     */
    private Long orderItemId;
    private ItemResponseDTO itemInfo;
    private int count;
    private int orderPrice;

    public OrderItemListVO(OrderItem orderItem) {
        this.orderItemId = orderItem.getId();
        this.itemInfo = new ItemResponseDTO(orderItem.getItem());
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
    }
}
