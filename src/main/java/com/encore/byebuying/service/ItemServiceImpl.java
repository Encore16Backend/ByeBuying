package com.encore.byebuying.service;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.repo.ItemRepository;
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
    private final CategoryRepo categoryRepo;
    private final ImageRepo imageRepo;

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
        Collection<Image> image = itemRepository.findById(itemid).get().getImages();
        Image img = image.iterator().next();
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
        return categoryRepo.save(category);
    }

    @Override
    public Image saveImage(Image image) {
        log.info("Saving new image {} to the database", image.getImgpath());
        return imageRepo.save(image);
    }

    @Override
    public void deleteImage(Image image) {
        log.info("delete item {} from the database", image.getImgpath());
        imageRepo.delete(image);
    }

    @Override
    public void addCategoryToItem(String itemName, String categoryName) {
        log.info("Adding Category {} to item {}", categoryName, itemName);
        Item item = itemRepository.findByName(itemName);
        Category category = categoryRepo.findByName(categoryName);
//        item.getCategories().add(category);
    }


    @Override
    public void addImageToItem(String itemName, String imgPath) {
        log.info("Adding Image Path {} to item {}", imgPath, itemName);
        Item item = itemRepository.findByName(itemName);
        Image image = imageRepo.findByImgpath(imgPath);
        item.getImages().add(image);
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
