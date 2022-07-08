package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.common.paging.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasketService {


//    BasketItemResponseDTO getById(Long id);
//    Page<BasketItem> findById(Pageable pageable, Long user_id);

    PagingResponse<BasketItem, BasketItemResponseDTO> getByUser(Pageable pageable, Long id);
    PagingResponse<BasketItem, BasketItemResponseDTO> getByItemName(Pageable pageable, BasketItemSearchDTO basketItemSearchDTO);
    void updateBasketItem(BasketUpdateDTO basketUpdateDTO);
    void addBasketItem(BasketItemAddDTO basketAddDTO);
    void deleteBasketItem(BasketItemDeleteDTO basketDeleteDTO);

}