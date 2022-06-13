package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasketDeleteDTO {
    private List<Long> item_ids;
    private Long user_id;
}
