package com.encore.byebuying.domain.basket.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CreateBasketItemDTO {
    @NotNull
    private Long itemId;
    @NotNull
    private Long userId;
    @Min(1)
    @Max(100)
    private int count;
}