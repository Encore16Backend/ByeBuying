package com.encore.byebuying.api.dto.basket;

import lombok.Data;

@Data
public class BasketDeleteRequest {
    private Long item_id;
    private Long user_id;
}
