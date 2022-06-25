package com.encore.byebuying.domain.basket.controller;

import com.encore.byebuying.domain.basket.dto.BasketAddDTO;
import com.encore.byebuying.domain.basket.dto.BasketDeleteDTO;
import com.encore.byebuying.domain.basket.dto.BasketUpdateDTO;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @GetMapping("/items")
    public ResponseEntity<?> getBasketItems(@RequestParam(defaultValue="",value="userId") Long userId,
                                            @RequestParam(required = false, defaultValue="1",value="page") int page) {
//        PageRequest pageRequest = PageRequest.of(page-1, 5);
//        Page<BasketItem> basketItems = basketService.findById(pageRequest, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<?> addBasket(@RequestBody BasketAddDTO basketAddDTO) {
        basketService.addBasketItem(basketAddDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateBasket(@RequestBody BasketUpdateDTO basketUpdateDTO) {
        basketService.updateBasketItem(basketUpdateDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/delete")
    public ResponseEntity<?> deleteBasket(@RequestBody BasketDeleteDTO basketDeleteDTO) {
        basketService.deleteBasketItem(basketDeleteDTO);
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
