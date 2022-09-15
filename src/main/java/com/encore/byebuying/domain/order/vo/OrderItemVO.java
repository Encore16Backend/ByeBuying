package com.encore.byebuying.domain.order.vo;

import lombok.Data;

@Data
public class OrderItemVO {
    private Long orderItemId;
    private int count;
    private int orderPrice;
}
