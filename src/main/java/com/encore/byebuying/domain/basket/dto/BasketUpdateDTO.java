package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasketUpdateDTO {
    private Long item_id;
    private Long user_id;
    private int count;
}