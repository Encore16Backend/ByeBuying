package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.BasketAndItem;
import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.common.paging.PagingResponse;
import org.springframework.data.domain.Pageable;

public interface BasketService {

    PagingResponse<BasketAndItem, BasketItemResponseDTO> getByUser(Pageable pageable, Long id);
    PagingResponse<BasketAndItem, BasketItemResponseDTO> getByItemName(Pageable pageable, BasketItemSearchDTO basketItemSearchDTO);
    void updateBasketItem(BasketUpdateDTO basketUpdateDTO);
    void addBasketItem(BasketItemAddDTO basketAddDTO);
    void deleteBasketItem(BasketItemDeleteDTO basketDeleteDTO);

}