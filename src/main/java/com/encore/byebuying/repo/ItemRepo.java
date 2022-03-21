package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> ,ItemRepoCustom{
    Page<Item> findAll(Pageable pageable);
    Page<Item> findByItemnameContainingIgnoreCase(Pageable pageable, String itemname);
    Page<Item> findByCategories_Cateid(Pageable pageable, Long cateid);
    Page<Item> findByCategories_CateidAndItemnameContainingIgnoreCase(Pageable pageable, Long cateid, String itemname);

    Item findByItemname(String itemname);
    Item findByItemid(Long itemid);
    // Best Item

    List<Item> findTop5ByOrderByPurchasecntDesc();
    List<Item> findTop5ByCategories_CateidOrderByPurchasecntDesc(Long cateid);

    // Category Item Sort
//    Page<Item> findAllByCategories_CateidOrderByPurchasecntDesc(Pageable pageable, Long cateid);
//    Page<Item> findAllByCategories_CateidOrderByReviewmeanDesc(Pageable pageable, Long cateid);
//    Page<Item> findAllByCategories_CateidOrderByPriceDesc(Pageable pageable, Long cateid);
//    Page<Item> findAllByCategories_CateidOrderByPriceAsc(Pageable pageable, Long cateid);

    @Query("select a from Item a where a.itemid in :id")
    List<Item> findImageRetrieval(Long[] id);

    void deleteByItemid(Long itemid);
}
