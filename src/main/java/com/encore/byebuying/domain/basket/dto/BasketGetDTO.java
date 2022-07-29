package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BasketGetDTO extends PagingRequest {
    @NotNull
    private Long userId;
}
