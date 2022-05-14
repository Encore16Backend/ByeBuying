package com.encore.byebuying.service.dto;

import com.encore.byebuying.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderItemsServiceOrderDto {
    private Long userId;
    private List<OrderItemInfoServiceDto> items;
    private Address address;
}
