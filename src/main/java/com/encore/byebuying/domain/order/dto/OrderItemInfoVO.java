package com.encore.byebuying.domain.order.dto;

import lombok.Data;

@Data
public class OrderItemInfoVO {
    private Long orderItemId;
    private int count;
    private int orderPrice;
}
