package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

@Data
public class BasketItemAddDTO {
    private Long itemId;
    private Long userId;
    private int count;
}