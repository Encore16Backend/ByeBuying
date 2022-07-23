package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BasketItemAddDTO {
    @NotNull
    private Long itemId;
    @NotNull
    private Long userId;
    @Min(1)
    private int count;

}