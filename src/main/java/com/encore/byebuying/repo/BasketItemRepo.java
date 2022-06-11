package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.domain.BasketItem;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {

    @Query("select b from BasketItem b where b.basket.id = :basket_id")
     Page<BasketItem> findByBasketId( Pageable pageable,@Param("basket_id") Long basket_id);

}
