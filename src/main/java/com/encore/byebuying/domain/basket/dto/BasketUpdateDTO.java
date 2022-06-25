package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasketUpdateDTO {
    private List<Long> item_ids; // 여러 개를 받을 필요 없음
    private Long user_id;
    private List<Integer> counts;
}
