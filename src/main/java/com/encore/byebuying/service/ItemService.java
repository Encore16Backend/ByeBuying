package com.encore.byebuying.service;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    List<Item> getItems(); // 전체 상품 조회

    Item saveItem(Item item);
    Category saveCategory(Category category);
    Image saveImage(Image image);

    void addCategoryToItem(String itemName, String categoryName);
    void addImageToItem(String itemName, String imgPath);

    List<Item> getTopItemOrderReviewMeanDesc();
    List<Item> getTopItemOrderPriceDesc();
    List<Item> getTopItemOrderPriceAsc();
    List<Item> getTopItemOrderPurchasecntDesc();

    List<Item> getTopItemByCategoryNameOrderByReviewMeanDesc(Long cateid);
    List<Item> getTopItemByCategoryNameOrderByPriceDesc(Long cateid);
    List<Item> getTopItemByCategoryNameOrderByPriceAsc(Long cateid);
    List<Item> getTopItemByCategoryNameOrderByPurchasecntDesc(Long cateid);
}
