package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query("select b.basketItems from Basket b where b.id = :id")
    Page<BasketItem> myFindAllById(Pageable pageable, @Param("id") Long id);

}