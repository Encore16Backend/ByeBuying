package com.encore.byebuying.api.dto.basket;

import lombok.Data;

@Data
public class BasketUpdateRequest {
    private Long item_id;
    private Long user_id;
    private int count;
}
