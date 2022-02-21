package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {
    Item findByItemname(String itemname);
    // Best Item
    // 1. 전체 카테고리
    // 1-1. 후기순
    // findTop300ByOrderBySeqDesc
    List<Item> findTop5ByOrderByReviewmeanDesc();
    // 1-2. 높은 가격순
    List<Item> findTop5ByOrderByPriceDesc();
    // 1-3. 낮은 가격순
    List<Item> findTop5ByOrderByPriceAsc();
    // 1-4. 판매순
    List<Item> findTop5ByOrderByPurchasecntDesc();
    // 2. 카테고리별
    // 2-1. 후기순
    List<Item> findTop5ByCategories_CateidOrderByReviewmeanDesc(Long cateid);
    // 2-2. 높은 가격순
    List<Item> findTop5ByCategories_CateidOrderByPriceDesc(Long cateid);
    // 2-3. 낮은 가격순
    List<Item> findTop5ByCategories_CateidOrderByPriceAsc(Long cateid);
    // 2-4. 판매순
    List<Item> findTop5ByCategories_CateidOrderByPurchasecntDesc(Long cateid);
}
