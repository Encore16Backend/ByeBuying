package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasketItemDeleteDTO {
    private List<Long> itemIds;
    private Long userId;
}