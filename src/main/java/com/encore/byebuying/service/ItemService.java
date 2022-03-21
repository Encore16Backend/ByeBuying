package com.encore.byebuying.service;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    Page<Item> getItems(Pageable pageable); // 전체 상품 조회
    Page<Item> getItems(Pageable pageable, String itemname);
    Page<Item> getItems(Pageable pageable, Long cateid);
    Page<Item> getItems(Pageable pageable, Long cateid, String itemname);


    Item getItemByItemname(String itemname);
    Item getItemByItemid(Long itemid);

    String getItemImagePath(Long itemid);

    Item saveItem(Item item);
    Category saveCategory(Category category);
    Image saveImage(Image image);

    void addCategoryToItem(String itemName, String categoryName);
    void addImageToItem(String itemName, String imgPath);

    List<Item> getTopItemOrderPurchasecntDesc();
    List<Item> getTopItemByCategoryNameOrderByPurchasecntDesc(Long cateid);

//    Page<Item> getItemByCategoryOrderByPurchaseDesc(Pageable pageable, Long cateid);
//    Page<Item> getItemByCategoryOrderByReviewmeanDesc(Pageable pageable, Long cateid);
//    Page<Item> getItemByCategoryOrderByPriceDesc(Pageable pageable, Long cateid);
//    Page<Item> getItemByCategoryOrderByPriceAsc(Pageable pageable, Long cateid);
    Page<Item> findBySearch(Pageable pageable, String searchName);

    List<Item> getItemRetrieval(Long[] ids);

    @Transactional
    void deleteItem(Long itemid);
}
