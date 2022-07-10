package com.encore.byebuying.domain.basket.controller;

import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.service.BasketService;
import com.encore.byebuying.domain.inquiry.dto.InquiryGetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    // 유저별 장바구니 상품 조회
    @GetMapping("/by-user")
    public ResponseEntity<?> getBasketByUSer(@RequestBody BasketGetDTO dto) {
        var basketItemsByUser = basketService.getByUser(dto.getPageRequest(), dto.getUserId());
        return new ResponseEntity<>(basketItemsByUser,HttpStatus.OK);
    }

    // 상품이름으로 장바구니 검색
    @GetMapping("/by-itemname")
    public ResponseEntity<?> getBasketItemByItemName(@RequestBody BasketItemSearchDTO dto) {
        var findItem = basketService.getByItemName(dto.getPageRequest(), dto);
        return new ResponseEntity<>(findItem,HttpStatus.OK);
    }

    // 장바구니 상품 추가
    @PostMapping("/item")
    public ResponseEntity<?> addBasket(@RequestBody BasketItemAddDTO basketAddDTO) {
        basketService.addBasketItem(basketAddDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    // 장바구니 상품 갯수 수정
    @PostMapping("/update-count")
    public ResponseEntity<?> updateBasket(@RequestBody BasketUpdateDTO basketUpdateDTO) {
        basketService.updateBasketItem(basketUpdateDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    // 장바구니 상품 삭제
    @PostMapping("/removal-item")
    public ResponseEntity<?> deleteBasket(@RequestBody BasketItemDeleteDTO basketDeleteDTO) {
        basketService.deleteBasketItem(basketDeleteDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}