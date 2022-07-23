package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.BasketAndItem;
import com.encore.byebuying.domain.basket.BasketItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long>{

    @Query(value = "select b as basketItem , b.item as item from BasketItem b left join b.item where b.basket.id = :basket_id")
    Page<BasketAndItem> findByBasketId(Pageable pageable, @Param("basket_id") Long basket_id);

    @Query("select b as basketItem , b.item as item from BasketItem b left join b.item where b.basket.id = :id and b.item.name like %:item_name%")
    Page<BasketAndItem> findByBasketIdAndItem_NameLike(Pageable pageable, Long id, String item_name);

    @Query("select b as basketItem , b.item as item from BasketItem b left join b.item where b.basket.id = :basket_id and b.item.name like %:item_name% and b.createdAt between :start_date and :end_date")
    Page<BasketAndItem> findByBasketIdAndCreatedAtBetween(Pageable pageable, Long basket_id, String item_name,
                                                          LocalDateTime start_date, LocalDateTime end_date);

}