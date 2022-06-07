package com.encore.byebuying.api;

import com.encore.byebuying.api.dto.basket.BasketAddRequest;
import com.encore.byebuying.api.dto.basket.BasketDeleteRequest;
import com.encore.byebuying.api.dto.basket.BasketUpdateRequest;
import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketResource {
    private final BasketService basketService;

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<?> addBasket(@RequestBody BasketAddRequest basketAddRequest) {
        basketService.addBasketItem(basketAddRequest);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateBasket(@RequestBody BasketUpdateRequest basketUpdateRequest) {
        basketService.updateBasketItem(basketUpdateRequest);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/delete")
    public ResponseEntity<?> deleteBasket(@RequestBody BasketDeleteRequest basketDeleteRequest) {
        basketService.deleteBasketItem(basketDeleteRequest);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }



//
//    @GetMapping("/byUsername")
//    public ResponseEntity<Page<Basket>> getBasketByUsername(
//            @RequestParam(defaultValue="",value="username") String username,
//            @RequestParam(required = false, defaultValue="1",value="page") int page) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        Pageable pageable = PageRequest.of(page-1, 5, sort);
//        Page<Basket> baskets = basketService.getByUsername(pageable, username);
//        return ResponseEntity.ok().body(baskets);
//    }
//
//
//
}
