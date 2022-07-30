package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BasketItemDeleteDTO {
    @NotBlank
    private List<Long> basketItemIds;
    // Validation 안됨, list안에 Long들까지 전파 안됨
    
    // item이 basket안에 들어있는지 확인 하기위해 item_id 확인
}