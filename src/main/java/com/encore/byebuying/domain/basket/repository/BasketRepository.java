package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

//
//    Page<BasketItem> findAllById(Pageable pageable, Long basket_id);
//    void deleteById(Long id);
//    void deleteByItemidAndUsername(Long itemid, String username);
//    void deleteAllByUsername(String username);
}
