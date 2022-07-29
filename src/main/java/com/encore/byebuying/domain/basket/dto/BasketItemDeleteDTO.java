package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BasketItemDeleteDTO  extends PagingRequest {
    @NotBlank
    private List<Long> basketItemIds;
    @NotNull
    private Long userId;

}