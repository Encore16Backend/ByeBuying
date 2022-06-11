package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.domain.BasketItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BasketRepo extends JpaRepository<Basket, Long> {

//
//    Page<BasketItem> findAllById(Pageable pageable, Long basket_id);
//    void deleteById(Long id);
//    void deleteByItemidAndUsername(Long itemid, String username);
//    void deleteAllByUsername(String username);
}
