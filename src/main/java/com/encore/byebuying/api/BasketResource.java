package com.encore.byebuying.api;

import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.service.BasketService;
import com.encore.byebuying.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketResource {
    private final BasketService basketService;

    @PostMapping("/add")
    public String addBasket(@RequestBody Basket basket) {
        basketService.saveBasket(basket, "save");
        return "SUCCESS";
    }

    @GetMapping("/byUsername")
    public ResponseEntity<Page<Basket>> getBasketByUsername(
            @RequestParam(defaultValue="",value="username") String username,
            @RequestParam(required = false, defaultValue="1",value="page") int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, 5, sort);
        Page<Basket> baskets = basketService.getByUsername(pageable, username);
        return ResponseEntity.ok().body(baskets);
    }

    @Transactional
    @PutMapping("/update")
    public String updateBasket(@RequestBody Basket changeBasket) {
        Basket basket = basketService.getBasketById(changeBasket.getId());
        basket.setBcount(changeBasket.getBcount());
        basketService.saveBasket(basket, "update");
        return "SUCCESS";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(
            @RequestParam(defaultValue = "", value="basketid[]") Long[] basketid){
        for (Long id: basketid) {
            basketService.deleteBasket(id);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
