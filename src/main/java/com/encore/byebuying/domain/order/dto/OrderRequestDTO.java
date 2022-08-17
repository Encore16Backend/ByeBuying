package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.common.Address;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class OrderRequestDTO {
    @NotBlank
    private Long userId;
    @NotBlank
    private List<OrderItemInfoDTO> items;
    @NotBlank
    private Address address;
}
