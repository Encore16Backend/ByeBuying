package com.encore.byebuying.service;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    List<Item> getItems(); // 전체 상품 조회
    Item getItemByItemname(String itemname);
    Item saveItem(Item item);
    Category saveCategory(Category category);
    Image saveImage(Image image);

    void addCategoryToItem(String itemName, String categoryName);
    void addImageToItem(String itemName, String imgPath);

    List<Item> getTopItemOrderPurchasecntDesc();
    List<Item> getTopItemByCategoryNameOrderByPurchasecntDesc(Long cateid);

    Page<Item> getItemByCategoryOrderByPurchaseDesc(Pageable pageable, Long cateid);
    Page<Item> getItemByCategoryOrderByReviewmeanDesc(Pageable pageable, Long cateid);
    Page<Item> getItemByCategoryOrderByPriceDesc(Pageable pageable, Long cateid);
    Page<Item> getItemByCategoryOrderByPriceAsc(Pageable pageable, Long cateid);
    Page<Item> findBySearch(Pageable pageable, String searchName);
}
