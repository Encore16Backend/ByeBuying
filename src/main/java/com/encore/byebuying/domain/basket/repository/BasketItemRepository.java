package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.BasketItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    Page<BasketItem> findByBasketId(Pageable pageable,@Param("basket_id") Long basket_id);
    @Query("select b from BasketItem b where b.basket.id = :id and b.item.name like %:item_name%")
    Page<BasketItem> findByBasketIdAndItem_NameLike(Pageable pageable, Long id, String item_name);
}