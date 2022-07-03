package com.encore.byebuying.domain.basket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasketItemSearchDTO {
    private String itemName;
    private Long user_id;
}
