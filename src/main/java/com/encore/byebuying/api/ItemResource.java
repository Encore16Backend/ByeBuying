package com.encore.byebuying.api;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
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
        List<Item> top = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(9L);
        List<Item> bottom = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(12L);
        List<Item> outer = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(15L);
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
}
