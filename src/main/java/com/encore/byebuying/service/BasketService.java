package com.encore.byebuying.service;

import com.encore.byebuying.api.dto.basket.BasketAddRequest;
import com.encore.byebuying.api.dto.basket.BasketDeleteRequest;
import com.encore.byebuying.api.dto.basket.BasketUpdateRequest;
import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.domain.BasketItem;
import com.encore.byebuying.domain.Order;
import com.encore.byebuying.service.dto.OrderItemsServiceOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

public interface BasketService {


//    Page<Order> findByUsername(Pageable pageable, String username);
//    Page<Order> findByUsernameAndBetweenDate(Pageable pageable, String username, String start, String end) throws ParseException;

    Page<BasketItem> findByUserId(Pageable pageable, Long user_id);
    @Transactional
    void updateBasketItem(BasketUpdateRequest basketUpdateRequest);
    @Transactional
    void addBasketItem(BasketAddRequest basketAddRequest);
    @Transactional
    void deleteBasketItem(BasketDeleteRequest basketDeleteRequest);
}
