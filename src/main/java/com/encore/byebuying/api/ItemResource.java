package com.encore.byebuying.api;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.service.ItemService;
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
public class ItemResource {
    private final ItemService itemService;
    private final CategoryRepo categoryRepo;
    private final ImageRepo imageRepo;

    @GetMapping("/items")
    public ResponseEntity<Page<Item>> getItems(
            @RequestParam(required = false, defaultValue = "", value = "category") String cateanme,
            @RequestParam(required = false, defaultValue = "", value = "itemname") String itemname,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        Pageable pageable = PageRequest.of(page-1, 5,
                Sort.by(Sort.Direction.ASC, "itemid"));
        Page<Item> items;
        if (itemname.equals("") && cateanme.equals("")) {
            items = itemService.getItems(pageable);
        } else if (cateanme.equals("")) { // 상품명만 있을 때
            items = itemService.getItems(pageable, itemname);
        } else { // 카테고리명은 무조건 있음
            Long cateid = categoryRepo.findByCatename(cateanme).getCateid();
            if (itemname.equals("")) {// 상품명 없음
                items = itemService.getItems(pageable, cateid);
            } else { // 상품명과 카테고리명 둘 다 있음
                items = itemService.getItems(pageable, cateid, itemname);
            }
        }
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

        Long topid = categoryRepo.findByCatename("상의").getCateid();
        Long bottomid = categoryRepo.findByCatename("바지").getCateid();
        Long outerid = categoryRepo.findByCatename("아우터").getCateid();

        List<Item> all = itemService.getTopItemOrderPurchasecntDesc();
        List<Item> top = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(topid);
        List<Item> bottom = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(bottomid);
        List<Item> outer = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(outerid);

        item.put("all", all);
        item.put("top", top);
        item.put("bottom", bottom);
        item.put("outer", outer);

        return ResponseEntity.ok().body(item);
    }

//    @GetMapping("/category/order") // 카테고리별 리뷰평점순
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
//                case 1: // 판매량순
//                    item = itemService.getItemByCategoryOrderByPurchaseDesc(pageable, cateid);
//                    break;
//                case 2: // 낮은 가격순
//                    item = itemService.getItemByCategoryOrderByPriceAsc(pageable, cateid);
//                    break;
//                case 3: // 높은 가격순
//                    item = itemService.getItemByCategoryOrderByPriceDesc(pageable, cateid);
//                    break;
//                case 4: // 후기순
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
        
        // 상품 등록
        ItemForm itemform = mapper.convertValue(itemSave.get("item"), ItemForm.class);
        Item item = itemService.saveItem(itemform.toEntity());

        // 생성한 상품에 카테고리 삽입
        ArrayList<String> categories = mapper.convertValue(itemSave.get("cate"), new TypeReference<ArrayList<String>>() {});
        for (String catename: categories) {
            itemService.addCategoryToItem(item.getItemname(), catename);
        }

        // 생성한 상품에 이미지 삽입
        ArrayList<String> images = mapper.convertValue(itemSave.get("images"), new TypeReference<ArrayList<String>>() {});
        for (String image: images) {
            itemService.saveImage(new Image(null, image));
            itemService.addImageToItem(item.getItemname(), image);
        }
        return ResponseEntity.created(uri).body(item);
    }

    /**
     * 상품정보 업데이트
     * */
    @PostMapping("/item/update")
    @Transactional
    public ResponseEntity<Item> updateItem(@RequestBody Map<String, Map<String, Object>> updateForm){
        Map<String, Object> updateItem =  updateForm.get("itemUpdate");
//        {
//            "itemUpdate" : {
//            "item": {
//                "itemname": "상품명",
//                        "price": 100000,
//                        "purchasecnt": 0,
//                        "count": 10,
//                        "reviewmean": 0,
//                        "reviewcount": 0
//            },
//            "cate": [
//            "상의", "긴팔"
//            ],
//            "images": ["테스트용","테스트용2"],
//            "itemId":45
//            }
//        }
        ObjectMapper mapper = new ObjectMapper();
        // pk 추출 후 해당 아이템을 꺼내옴
        Long itemId = mapper.convertValue(updateItem.get("itemId"), Long.class);
        Item item = itemService.getItemByItemid(itemId);

        // 수정 엔티티 값들 추출 (기본) 후 수정
        ItemForm itemform = mapper.convertValue(updateItem.get("item"), ItemForm.class);
        item.setItemname(itemform.getItemname());
        item.setPrice(itemform.getPrice());
        item.setPurchasecnt(itemform.getPurchasecnt());
        item.setCount(itemform.getCount());
        item.setReviewmean(itemform.getReviewmean());
        item.setReviewcount(itemform.getReviewcount());
        // 카테고리 추출 후 삽입
        ArrayList<String> categories = mapper.convertValue(updateItem.get("cate"), new TypeReference<ArrayList<String>>() {});
        System.out.println("categories = " + categories);
        item.getCategories().clear();
        for (String catename: categories) {
            itemService.addCategoryToItem(item.getItemname(), catename);
        }
        // 생성한 상품에 기존 이미지 경로 삭제하고 이미지 삽입
        ArrayList<String> images = mapper.convertValue(updateItem.get("images"), new TypeReference<ArrayList<String>>() {});
        System.out.println("images = " + images);
        // 들어온 이미지가 있으면 재등록
        if (images != null || images.size() != 0){
            // 기존 아이템이 가지고 있던 이미지 삭제
            Collection<Image> images_to_delete = item.getImages();
            for (Image image_to_delete : images_to_delete) {
                itemService.deleteImage(image_to_delete);
            }
            // item이 가지고 있던 이미지 리스트 삭제
            images_to_delete.clear();
            // 이미지 재등록
            for (String image: images) {
                itemService.saveImage(new Image(null, image));
                itemService.addImageToItem(item.getItemname(), image);
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
    
    @GetMapping("/search")
    public ResponseEntity<Page<Item>> search(
            @RequestParam(defaultValue = "", value = "searchName") String searchName,
            @RequestParam(defaultValue = "DESC", value = "asc") String asc,
			@RequestParam(defaultValue="reviewmean",value="sortname") String sortname,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
    	Sort sort;
    	if(asc.equals("ASC") || asc.equals("asc")) {
    		sort = Sort.by(Sort.Direction.ASC, sortname);
    	}else {
    		sort = Sort.by(Sort.Direction.DESC, sortname);
    	}
    	
    	Pageable pageable = PageRequest.of(page-1, 10,sort);
    	Page<Item> item = itemService.findBySearch(pageable,searchName);

        return ResponseEntity.ok().body(item);
    }

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

@Data
class CategoryToItemForm {
    private String itemName;
    private String categoryName;
}

@Data
class ImageToItemForm {
    private String itemName;
    private String imgPath;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ItemForm {
    private String itemname;
    private int price;
    private int purchasecnt;
    private int count;
    private double reviewmean;
    private int reviewcount;

    public Item toEntity() {
        return Item.builder()
                .itemname(this.itemname)
                .price(this.price)
                .purchasecnt(this.purchasecnt)
                .count(this.count)
                .reviewmean(this.reviewmean)
                .reviewcount(this.reviewcount)
                .categories(new ArrayList<>())
                .images(new ArrayList<>())
                .build();
    }
}