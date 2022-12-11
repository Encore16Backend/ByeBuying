package com.encore.byebuying.domain.order.dto;

import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.order.vo.OrderItemVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class OrderRequestDTO {
    @NotBlank
    private Long userId;
    @NotBlank
    private List<OrderItemVO> items;
    private Address address;
    private Long locationId;
}
