package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.inquiry.dto.InquiryListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface BasketService {


//    Page<Order> findByUsername(Pageable pageable, String username);
//    Page<Order> findByUsernameAndBetweenDate(Pageable pageable, String username, String start, String end) throws ParseException;

    BasketItemListDTO getBasketItems(Long user_id);
    BasketItemDTO getById(Long id);
    Page<BasketItem> findById(Pageable pageable, Long user_id);
    void updateBasketItem(BasketUpdateDTO basketUpdateDTO);
    void addBasketItem(BasketAddDTO basketAddDTO);
    void deleteBasketItem(BasketDeleteDTO basketDeleteDTO);

}