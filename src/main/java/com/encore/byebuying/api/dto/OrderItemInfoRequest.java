package com.encore.byebuying.api.dto;

import lombok.Data;

@Data
public class OrderItemInfoRequest {
    private Long itemId;
    private int count;
    private int orderPrice;
}
