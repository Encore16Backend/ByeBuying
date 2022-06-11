package com.encore.byebuying.api.dto.basket;

import lombok.Data;

@Data
public class BasketAddRequest {
    private Long item_id;
    private Long user_id;
    private int count;
}
