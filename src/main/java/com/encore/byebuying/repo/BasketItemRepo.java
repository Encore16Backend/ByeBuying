package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.domain.BasketItem;
import com.encore.byebuying.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
    Page<BasketItem> findByBasketId(Pageable pageable, Long user_id);
}
