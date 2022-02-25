package com.encore.byebuying.api;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Image;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.service.ItemService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Item>> getItems() {
        List<Item> item = itemService.getItems();
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/bestItem")
    public ResponseEntity<Map<String, Object>> getBestItems() {
        Map<String, Object> item = new HashMap<>();
        List<Item> all = itemService.getTopItemOrderPurchasecntDesc();
        List<Item> top = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(1L);
        List<Item> bottom = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(4L);
        List<Item> outer = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(7L);
        item.put("all", all);
        item.put("top", top);
        item.put("bottom", bottom);
        item.put("outer", outer);

        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/category/order") // 카테고리별 리뷰평점순
    public ResponseEntity<Page<Item>> getCategoryOrderByReview(
            @RequestParam(defaultValue = "", value = "category") Long cateid,
            @RequestParam(defaultValue = "1", value = "order") int order,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<Item> item;
        switch (order) {
            case 1: // 판매량순
                item = itemService.getItemByCategoryOrderByPurchaseDesc(pageable, cateid);
                break;
            case 2: // 낮은 가격순
                item = itemService.getItemByCategoryOrderByPriceAsc(pageable, cateid);
                break;
            case 3: // 높은 가격순
                item = itemService.getItemByCategoryOrderByPriceDesc(pageable, cateid);
                break;
            case 4: // 후기순
                item = itemService.getItemByCategoryOrderByReviewmeanDesc(pageable, cateid);
                break;
            default:
                item = null;
                break;
        }
        return ResponseEntity.ok().body(item);
    }

    @PostMapping("/item/save")
    public ResponseEntity<Item> saveItem(@RequestBody ItemForm itemForm) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/main/item/save").toUriString());

        Item item = itemForm.toEntity();
        return ResponseEntity.created(uri).body(itemService.saveItem(item));
    }

    @PostMapping("/category/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/main/category/save").toUriString());
        return ResponseEntity.created(uri).body(itemService.saveCategory(category));
    }

    @PostMapping("/image/save")
    public ResponseEntity<Image> saveImage(@RequestBody Image image) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/main/image/save").toUriString());
        return ResponseEntity.created(uri).body(itemService.saveImage(image));
    }

    @PostMapping("/category/add-to-item")
    public ResponseEntity<?> addCategoryToItem(@RequestBody CategoryToItemForm form) {
        itemService.addCategoryToItem(form.getItemName(), form.getCategoryName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/image/add-to-item")
    public ResponseEntity<?> addImageToItem(@RequestBody ImageToItemForm form) {
        itemService.addImageToItem(form.getItemName(), form.getImgPath());
        return ResponseEntity.ok().build();
    }
    
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
                .reviewmean(this.reviewcount)
                .reviewcount(this.reviewcount)
                .categories(new ArrayList<>())
                .images(new ArrayList<>())
                .build();
    }
}