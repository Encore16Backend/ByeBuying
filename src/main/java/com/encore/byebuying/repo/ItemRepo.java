package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> ,ItemRepoCustom{
    Item findByItemname(String itemname);
    Item findByItemid(Long itemid);
    // Best Item

    List<Item> findTop5ByOrderByPurchasecntDesc();
    List<Item> findTop5ByCategories_CateidOrderByPurchasecntDesc(Long cateid);

    // Category Item Sort
    Page<Item> findAllByCategories_CateidOrderByPurchasecntDesc(Pageable pageable, Long cateid);
    Page<Item> findAllByCategories_CateidOrderByReviewmeanDesc(Pageable pageable, Long cateid);
    Page<Item> findAllByCategories_CateidOrderByPriceDesc(Pageable pageable, Long cateid);
    Page<Item> findAllByCategories_CateidOrderByPriceAsc(Pageable pageable, Long cateid);
}
