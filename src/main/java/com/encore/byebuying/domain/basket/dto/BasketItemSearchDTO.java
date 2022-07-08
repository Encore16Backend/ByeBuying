package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasketItemSearchDTO extends PagingRequest {
    private String itemName;
    private Long user_id;
}
