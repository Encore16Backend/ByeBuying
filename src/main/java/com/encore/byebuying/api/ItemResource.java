package com.encore.byebuying.api;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/order/review")
    public ResponseEntity<List<Item>> getTopItemOrderByReviewDesc() {
        List<Item> item = itemService.getTopItemOrderReviewMeanDesc();
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/order/price1")
    public ResponseEntity<List<Item>> getTopItemOrderPrice1() {
        List<Item> item = itemService.getTopItemOrderPriceDesc();
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/order/price2")
    public ResponseEntity<List<Item>> getTopItemOrderPrice2() {
        List<Item> item = itemService.getTopItemOrderPriceAsc();
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/order/purchase")
    public ResponseEntity<List<Item>> getTopItemOrderPurchase() {
        List<Item> item = itemService.getTopItemOrderPurchasecntDesc();
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/category/review") // 카테고리별 리뷰평점순 베스트 5
    public ResponseEntity<List<Item>> getTopItemByCategoryOrderByReview(
            @RequestParam(defaultValue = "", value = "category") Long cateid) {
        List<Item> item = itemService.getTopItemByCategoryNameOrderByReviewMeanDesc(cateid);
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/category/price1") // 카테고리별 리뷰평점순 베스트 5
    public ResponseEntity<List<Item>> getTopItemByCategoryOrderByPrice1(
            @RequestParam(defaultValue = "", value = "category") Long cateid) {
        List<Item> item = itemService.getTopItemByCategoryNameOrderByPriceDesc(cateid);
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/category/price2") // 카테고리별 리뷰평점순 베스트 5
    public ResponseEntity<List<Item>> getTopItemByCategoryOrderByPrice2(
            @RequestParam(defaultValue = "", value = "category") Long cateid) {
        List<Item> item = itemService.getTopItemByCategoryNameOrderByPriceAsc(cateid);
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/category/purchase") // 카테고리별 리뷰평점순 베스트 5
    public ResponseEntity<List<Item>> getTopItemByCategoryOrderByPurchase(
            @RequestParam(defaultValue = "", value = "category") Long cateid) {
        List<Item> item = itemService.getTopItemByCategoryNameOrderByPurchasecntDesc(cateid);
        return ResponseEntity.ok().body(item);
    }
}
