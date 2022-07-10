package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasketUpdateDTO {
    private Long basketItemId;
    private int count;
}