package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BasketItemAddDTO extends PagingRequest {
    @NotNull
    private Long itemId;
    @NotNull
    private Long userId;
    @Min(1)
    @Max(100)
    private int count;

}