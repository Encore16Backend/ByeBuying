package com.encore.byebuying.service;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.repo.ItemRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepo itemRepo;
    private final CategoryRepo categoryRepo;
    private final ImageRepo imageRepo;

    @Override
    public Page<Item> getItems(Pageable pageable) {
        return itemRepo.findAll(pageable);
    }

    @Override
    public Page<Item> getItems(Pageable pageable, String itemname) {
        return itemRepo.findByItemnameContainingIgnoreCase(pageable, itemname);
    }

    @Override
    public Page<Item> getItems(Pageable pageable, Long cateid) {
        return itemRepo.findByCategories_Cateid(pageable, cateid);
    }

    @Override
    public Page<Item> getItems(Pageable pageable, Long cateid, String itemname) {
        return itemRepo.findByCategories_CateidAndItemnameContainingIgnoreCase(pageable, cateid, itemname);
    }

    @Override
    public Item getItemByItemname(String itemname) {
        log.info("Get Item by name: {}", itemname);
        return itemRepo.findByItemname(itemname);
    }

    @Override
    public Item getItemByItemid(Long itemid) {
        log.info("Get Item By id: {}", itemid);
        return itemRepo.findByItemid(itemid);
    }

    @Override
    public String getItemImagePath(Long itemid) {
        Collection<Image> image = itemRepo.findByItemid(itemid).getImages();
        Image img = image.iterator().next();
        return img.getImgpath();
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

    @Override
    public List<Item> getTopItemOrderPurchasecntDesc() {
        log.info("get Top 5 Item All Category order by PurchaseCnt");
        return itemRepo.findTop5ByOrderByPurchasecntDesc();
    }

    @Override
    public List<Item> getTopItemByCategoryNameOrderByPurchasecntDesc(Long cateid) {
        log.info("Get Top 5 Item by category {} Order By PurchaseCnt Desc", cateid);
        return itemRepo.findTop5ByCategories_CateidOrderByPurchasecntDesc(cateid);
    }

//    @Override
//    public Page<Item> getItemByCategoryOrderByPurchaseDesc(Pageable pageable, Long cateid) {
//        return itemRepo.findAllByCategories_CateidOrderByPurchasecntDesc(pageable, cateid);
//    }
//
//    @Override
//    public Page<Item> getItemByCategoryOrderByReviewmeanDesc(Pageable pageable, Long cateid) {
//        return itemRepo.findAllByCategories_CateidOrderByReviewmeanDesc(pageable, cateid);
//    }
//
//    @Override
//    public Page<Item> getItemByCategoryOrderByPriceDesc(Pageable pageable, Long cateid) {
//        return itemRepo.findAllByCategories_CateidOrderByPriceDesc(pageable, cateid);
//    }
//
//    @Override
//    public Page<Item> getItemByCategoryOrderByPriceAsc(Pageable pageable, Long cateid) {
//        return itemRepo.findAllByCategories_CateidOrderByPriceAsc(pageable, cateid);
//    }

    @Override
    public Page<Item> findBySearch(Pageable pageable, String searchName) {
    	//return itemRepo.findByCategoriesInAndItemnameIn(pageable,cateList, itemname);
    	return itemRepo.findBySearch(pageable,searchName);
    }

    @Override
    public List<Item> getItemRetrieval(Long[] ids) {
        return itemRepo.findImageRetrieval(ids);
    }

    @Override
    public void deleteItem(Long itemid) {
        itemRepo.deleteByItemid(itemid);
    }
}
