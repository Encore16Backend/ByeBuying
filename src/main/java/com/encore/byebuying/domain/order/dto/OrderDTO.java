package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.common.Address;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long userId;
    private List<OrderItemInfoDTO> items;
    private Address address;
}
