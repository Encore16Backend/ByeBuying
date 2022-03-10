package com.encore.byebuying.service;

import com.encore.byebuying.domain.Basket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketService {
    Basket saveBasket(Basket basket, String mode); // 장바구니 추가

    Page<Basket> getByUsername(Pageable pageable, String username); // 장바구니 목록

    Basket getBasketById(Long id);

    @Transactional
    void deleteBasket(Long id);

    @Transactional
    void deleteBasketByItemidAndUsername(Long itemid, String username);

}
