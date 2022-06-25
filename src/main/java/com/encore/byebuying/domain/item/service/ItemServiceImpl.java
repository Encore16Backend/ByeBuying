package com.encore.byebuying.domain.item.service;

import com.encore.byebuying.domain.item.ItemImage;
import com.encore.byebuying.domain.order.Category;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.CategoryRepository;
import com.encore.byebuying.domain.item.repository.ItemImageRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemImageRepository itemImageRepository;

    @Override
    public Page<Item> getItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Item getItemByItemid(Long itemid) {
        log.info("Get Item By id: {}", itemid);
        return itemRepository.findById(itemid).get();
    }

    @Override
    public String getItemImagePath(Long itemid) {
        Collection<ItemImage> itemImage = itemRepository.findById(itemid).get().getItemImages();
        ItemImage img = itemImage.iterator().next();
        return img.getImgpath();
    }

    @Override
    public Item saveItem(Item item) {
        log.info("Saving new item {} to the database", item.getName());
        return itemRepository.save(item);
    }


    @Override
    public Category saveCategory(Category category) {
        log.info("Saving new Category {} to the databases", category.getName());
        return categoryRepository.save(category);
    }

    @Override
    public ItemImage saveImage(ItemImage itemImage) {
        log.info("Saving new image {} to the database", itemImage.getImgpath());
        return itemImageRepository.save(itemImage);
    }

    @Override
    public void deleteImage(ItemImage itemImage) {
        log.info("delete item {} from the database", itemImage.getImgpath());
        itemImageRepository.delete(itemImage);
    }

    @Override
    public void addCategoryToItem(String itemName, String categoryName) {
        log.info("Adding Category {} to item {}", categoryName, itemName);
        Item item = itemRepository.findByName(itemName);
        Category category = categoryRepository.findByName(categoryName);
//        item.getCategories().add(category);
    }


    @Override
    public void addImageToItem(String itemName, String imgPath) {
        log.info("Adding Image Path {} to item {}", imgPath, itemName);
        Item item = itemRepository.findByName(itemName);
        ItemImage itemImage = itemImageRepository.findByImgpath(imgPath);
        item.getItemImages().add(itemImage);
    }

//    @Override
//    public Page<Item> findBySearch(Pageable pageable, String searchName) {
//    	//return itemRepo.findByCategoriesInAndItemnameIn(pageable,cateList, itemname);
//    	return itemRepository.findBySearch(pageable,searchName);
//    }

    @Override
    public List<Item> getItemRetrieval(Long[] ids) {
        return itemRepository.findImageRetrieval(ids);
    }

    @Override
    public void deleteItem(Long itemid) {
        itemRepository.deleteById(itemid);
    }
}
