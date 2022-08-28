package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

}