package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BasketItemSearchDTO extends PagingRequest {
    
    // 없으면 전부다 검색
    @NotNull
    private String itemName = "";
    @NotNull
    private Long userId;

}
