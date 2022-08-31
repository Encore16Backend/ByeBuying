package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.item.dto.ItemResponseDTO;
import com.encore.byebuying.domain.order.OrderItem;
import lombok.Data;


@Data
public class OrderItemResponseVO {
    // TODO: 2022-08-14 모든 response DTO -> VO 로 변경
    // Response 들은 다음과 같이 변경될거임
    // ListVO / DetailVO
    // Entity+VO
    // ListVO, DetailVO, DefaultVO
    private ItemResponseDTO item;
    private int orderPrice;
    private int count;

    public OrderItemResponseVO(OrderItem orderItem) {
        this.item = new ItemResponseDTO(orderItem.getItem());
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
