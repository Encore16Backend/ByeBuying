package com.encore.byebuying.api.dto;

import com.encore.byebuying.domain.Address;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private List<OrderItemInfoRequest> items;
    private Address address;
}
