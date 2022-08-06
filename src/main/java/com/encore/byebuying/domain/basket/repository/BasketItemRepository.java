package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long>, CustomBasketItemRepository{


}