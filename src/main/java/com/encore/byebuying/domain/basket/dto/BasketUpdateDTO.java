package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BasketUpdateDTO extends PagingRequest {
    @NotNull
    private Long basketItemId;
    @NotNull
    private Long userId;
    @NotBlank
    @Min(1)
    private int count;
}