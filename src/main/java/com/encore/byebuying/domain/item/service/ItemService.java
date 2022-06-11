package com.encore.byebuying.domain.item.service;

import com.encore.byebuying.domain.item.ItemImage;
import com.encore.byebuying.domain.order.Category;
import com.encore.byebuying.domain.item.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemService {
    Page<Item> getItems(Pageable pageable); // 전체 상품 조회
    Item getItemByItemid(Long itemid);
    String getItemImagePath(Long itemid);

    Item saveItem(Item item);

    Category saveCategory(Category category);
    ItemImage saveImage(ItemImage itemImage);

    void deleteImage(ItemImage itemImage);

    void addCategoryToItem(String itemName, String categoryName);
    void addImageToItem(String itemName, String imgPath);

//    Page<Item> findBySearch(Pageable pageable, String searchName);

    List<Item> getItemRetrieval(Long[] ids);

    @Transactional
    void deleteItem(Long itemid);
}
