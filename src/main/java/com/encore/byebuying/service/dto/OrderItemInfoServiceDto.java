package com.encore.byebuying.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemInfoServiceDto {
    private Long itemId;
    private int count;
    private int orderPrice;
}
