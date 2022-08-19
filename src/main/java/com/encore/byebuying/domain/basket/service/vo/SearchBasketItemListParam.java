package com.encore.byebuying.domain.basket.service.vo;

import com.encore.byebuying.domain.basket.dto.SearchBasketItemListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBasketItemListParam {

    Long basketId;
    private Long userId;
    // 없으면 전부다 검색
    private String itemName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;



    public static SearchBasketItemListParam valueOf(SearchBasketItemListDTO dto, Long basketId) {
        SearchBasketItemListParam param = new SearchBasketItemListParam();
        param.setBasketId(basketId);
        param.setItemName(dto.getItemName());
        param.setUserId(dto.getUserId());
        param.setStartDate(dto.getStartDate());
        param.setStartDate(dto.getEndDate());
        return param;
    }

}