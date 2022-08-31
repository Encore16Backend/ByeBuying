package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.common.Address;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class OrderRequestVO {
    @NotBlank
    private Long userId;
    @NotBlank
    private List<OrderItemInfoVO> items;
    private Address address;
    private Long locationId;
}
