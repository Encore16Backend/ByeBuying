package com.encore.byebuying.domain.order.dto;

import lombok.Data;

@Data
public class OrderItemInfoDTO {
    private Long itemId;
    private int count;
    private int orderPrice;
}
