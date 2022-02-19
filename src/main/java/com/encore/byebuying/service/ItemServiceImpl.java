package com.encore.byebuying.service;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.repo.ItemRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepo itemRepo;
    private final CategoryRepo categoryRepo;
    private final ImageRepo imageRepo;

    @Override
    public List<Item> getItems() {
        log.info("Get All Item");
        return itemRepo.findAll();
    }

    @Override
    public Item saveItem(Item item) {
        log.info("Saving new item {} to the database", item.getItemname());
        return itemRepo.save(item);
    }

    @Override
    public Category saveCategory(Category category) {
        log.info("Saving new Category {} to the databases", category.getCatename());
        return categoryRepo.save(category);
    }

    @Override
    public Image saveImage(Image image) {
        log.info("Saving new image {} to the database", image.getImgpath());
        return imageRepo.save(image);
    }

    @Override
    public void addCategoryToItem(String itemName, String categoryName) {
        log.info("Adding Category {} to item {}", categoryName, itemName);
        Item item = itemRepo.findByItemname(itemName);
        Category category = categoryRepo.findByCatename(categoryName);
        item.getCategories().add(category);
    }

    @Override
    public void addImageToItem(String itemName, String imgPath) {
        log.info("Adding Image Path {} to item {}", imgPath, itemName);
        Item item = itemRepo.findByItemname(itemName);
        Image image = imageRepo.findByImgpath(imgPath);
        item.getImages().add(image);
    }


}
