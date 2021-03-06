package com.encore.byebuying.domain.item.controller;

import com.encore.byebuying.domain.item.ItemImage;
import com.encore.byebuying.domain.item.dto.ItemAddDTO;
import com.encore.byebuying.domain.category.Category;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.category.repository.CategoryRepository;
import com.encore.byebuying.domain.item.repository.ItemImageRepository;
import com.encore.byebuying.domain.item.service.ItemService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor @Slf4j
public class ItemController {
    private final ItemService itemService;
    private final CategoryRepository categoryRepository;
    private final ItemImageRepository itemImageRepository;

    @GetMapping("/items")
    public ResponseEntity<Page<Item>> getItems(
            @RequestParam(required = false, defaultValue = "", value = "category") String cateanme,
            @RequestParam(required = false, defaultValue = "", value = "itemname") String itemname,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        Pageable pageable = PageRequest.of(page-1, 5,
                Sort.by(Sort.Direction.ASC, "itemid"));
        Page<Item> items;

        items = itemService.getItems(pageable);
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/itemImg")
    public ResponseEntity<String> getItemImagePath(
            @RequestParam(defaultValue = "", value = "itemid") Long itemid) {
        return ResponseEntity.ok().body(itemService.getItemImagePath(itemid));
    }

    @GetMapping("/bestItem")
    public ResponseEntity<Map<String, Object>> getBestItems() {
        Map<String, Object> item = new HashMap<>();

//        Long topid = categoryRepo.findByCatename("??????").getCateid();
//        Long bottomid = categoryRepo.findByCatename("??????").getCateid();
//        Long outerid = categoryRepo.findByCatename("?????????").getCateid();
//
//        List<Item> all = itemService.getTopItemOrderPurchasecntDesc();
//        List<Item> top = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(topid);
//        List<Item> bottom = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(bottomid);
//        List<Item> outer = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(outerid);
//
//        item.put("all", all);
//        item.put("top", top);
//        item.put("bottom", bottom);
//        item.put("outer", outer);

        return ResponseEntity.ok().body(item);
    }

//    @GetMapping("/category/order") // ??????????????? ???????????????
//    public ResponseEntity<Page<Item>> getCategoryOrderByReview(
//            @RequestParam(defaultValue = "", value = "category") String cateanme,
//            @RequestParam(defaultValue = "1", value = "order") int order,
//            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
//        Pageable pageable = PageRequest.of(page-1, 10);
//        Page<Item> item;
//        Long cateid;
//        try {
//            cateid = categoryRepo.findByCatename(cateanme).getCateid();
//            switch (order) {
//                case 1: // ????????????
//                    item = itemService.getItemByCategoryOrderByPurchaseDesc(pageable, cateid);
//                    break;
//                case 2: // ?????? ?????????
//                    item = itemService.getItemByCategoryOrderByPriceAsc(pageable, cateid);
//                    break;
//                case 3: // ?????? ?????????
//                    item = itemService.getItemByCategoryOrderByPriceDesc(pageable, cateid);
//                    break;
//                case 4: // ?????????
//                    item = itemService.getItemByCategoryOrderByReviewmeanDesc(pageable, cateid);
//                    break;
//                default:
//                    item = null;
//                    break;
//            }
//            return ResponseEntity.ok().body(item);
//        } catch (NullPointerException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

//    @PostMapping("/item/save")
//    public ResponseEntity<Item> saveItem(@RequestBody ItemForm itemForm) {
//        URI uri = URI.create(
//                ServletUriComponentsBuilder
//                        .fromCurrentContextPath()
//                        .path("/main/item/save").toUriString());
//        Item item = itemForm.toEntity();
//
//        return ResponseEntity.created(uri).body(itemService.saveItem(item));
//    }

    @PostMapping("/item/save")
    public ResponseEntity<Item> saveItem(@RequestBody Map<String, Map<String, Object>> saveForm) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/main/item/save").toUriString());
        
        Map<String, Object> itemSave =  saveForm.get("itemSave");
        ObjectMapper mapper = new ObjectMapper();
        
        // ?????? ??????
        ItemAddDTO itemform = mapper.convertValue(itemSave.get("item"), ItemAddDTO.class);
        Item item = itemService.saveItem(itemform.toEntity());

        // ????????? ????????? ???????????? ??????
        ArrayList<String> categories = mapper.convertValue(itemSave.get("cate"), new TypeReference<ArrayList<String>>() {});
        for (String catename: categories) {
            itemService.addCategoryToItem(item.getName(), catename);
        }

        // ????????? ????????? ????????? ??????
        ArrayList<String> images = mapper.convertValue(itemSave.get("images"), new TypeReference<ArrayList<String>>() {});
        for (String image: images) {
            itemService.saveImage(new ItemImage(null, image));
            itemService.addImageToItem(item.getName(), image);
        }
        return ResponseEntity.created(uri).body(item);
    }

    /**
     * ???????????? ????????????
     * */
    @PostMapping("/item/update")
    @Transactional
    public ResponseEntity<Item> updateItem(@RequestBody Map<String, Map<String, Object>> updateForm){
        Map<String, Object> updateItem =  updateForm.get("itemUpdate");
//        {
//            "itemUpdate" : {
//            "item": {
//                "itemname": "?????????",
//                        "price": 100000,
//                        "purchasecnt": 0,
//                        "count": 10,
//                        "reviewmean": 0,
//                        "reviewcount": 0
//            },
//            "cate": [
//            "??????", "??????"
//            ],
//            "images": ["????????????","????????????2"],
//            "itemId":45
//            }
//        }
        ObjectMapper mapper = new ObjectMapper();
        // pk ?????? ??? ?????? ???????????? ?????????
        Long itemId = mapper.convertValue(updateItem.get("itemId"), Long.class);
        Item item = itemService.getItemByItemid(itemId);

        // ?????? ????????? ?????? ?????? (??????) ??? ??????
//        ItemForm itemform = mapper.convertValue(updateItem.get("item"), ItemForm.class);
//        item.setName(itemform.getItemname());
//        item.setPrice(itemform.getPrice());
//        item.setPurchasecnt(itemform.getPurchasecnt());
//        item.setCount(itemform.getCount());
//        item.setReviewmean(itemform.getReviewmean());
//        item.setReviewcount(itemform.getReviewcount());
        // ???????????? ?????? ??? ??????
        ArrayList<String> categories = mapper.convertValue(updateItem.get("cate"), new TypeReference<ArrayList<String>>() {});
        System.out.println("categories = " + categories);
//        item.getCategories().clear();
        for (String catename: categories) {
            itemService.addCategoryToItem(item.getName(), catename);
        }
        // ????????? ????????? ?????? ????????? ?????? ???????????? ????????? ??????
        ArrayList<String> images = mapper.convertValue(updateItem.get("images"), new TypeReference<ArrayList<String>>() {});
        System.out.println("images = " + images);
        // ????????? ???????????? ????????? ?????????
        if (images != null || images.size() != 0){
            // ?????? ???????????? ????????? ?????? ????????? ??????
            Collection<ItemImage> images_to_delete = item.getItemImages();
            for (ItemImage itemImage_to_delete : images_to_delete) {
                itemService.deleteImage(itemImage_to_delete);
            }
            // item??? ????????? ?????? ????????? ????????? ??????
            images_to_delete.clear();
            // ????????? ?????????
            for (String image: images) {
                itemService.saveImage(new ItemImage(null, image));
                itemService.addImageToItem(item.getName(), image);
            }
        }



        return ResponseEntity.ok().body(item);
    }

    @PostMapping("/category/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/main/category/save").toUriString());
        return ResponseEntity.created(uri).body(itemService.saveCategory(category));
    }

//    @PostMapping("/image/save")
//    public ResponseEntity<Image> saveImage(@RequestBody Image image) {
//        URI uri = URI.create(
//                ServletUriComponentsBuilder
//                        .fromCurrentContextPath()
//                        .path("/main/image/save").toUriString());
//        return ResponseEntity.created(uri).body(itemService.saveImage(image));
//    }
//
//    @PostMapping("/category/add-to-item")
//    public ResponseEntity<?> addCategoryToItem(@RequestBody CategoryToItemForm form) {
//        itemService.addCategoryToItem(form.getItemName(), form.getCategoryName());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/image/add-to-item")
//    public ResponseEntity<?> addImageToItem(@RequestBody ImageToItemForm form) {
//        itemService.addImageToItem(form.getItemName(), form.getImgPath());
//        return ResponseEntity.ok().build();
//    }
    
//    @GetMapping("/search")
//    public ResponseEntity<Page<Item>> search(
//            @RequestParam(defaultValue = "", value = "searchName") String searchName,
//            @RequestParam(defaultValue = "DESC", value = "asc") String asc,
//			@RequestParam(defaultValue="reviewmean",value="sortname") String sortname,
//            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
//    	Sort sort;
//    	if(asc.equals("ASC") || asc.equals("asc")) {
//    		sort = Sort.by(Sort.Direction.ASC, sortname);
//    	}else {
//    		sort = Sort.by(Sort.Direction.DESC, sortname);
//    	}
//
//    	Pageable pageable = PageRequest.of(page-1, 10,sort);
////    	Page<Item> item = itemService.findBySearch(pageable,searchName);
//
//        return ResponseEntity.ok().body(item);
//    }

    @GetMapping("/item")
    public ResponseEntity<Item> getItem(
            @RequestParam(defaultValue = "", value = "itemid") Long itemid) {
        return ResponseEntity.ok().body(itemService.getItemByItemid(itemid));
    }

    @DeleteMapping("/item/delete")
    public ResponseEntity<?> deleteItem(
            @RequestParam(defaultValue = "", value = "itemid") Long itemid) {
        itemService.deleteItem(itemid);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}

//@Data
//class CategoryToItemForm {
//    private String itemName;
//    private String categoryName;
//}
//
//@Data
//class ImageToItemForm {
//    private String itemName;
//    private String imgPath;
//}