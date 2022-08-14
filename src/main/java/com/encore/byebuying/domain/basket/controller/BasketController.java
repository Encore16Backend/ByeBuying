package com.encore.byebuying.domain.basket.controller;

import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.service.BasketService;
import com.encore.byebuying.domain.basket.service.vo.BasketItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    // 유저별 장바구니 상품 조회
    @GetMapping("/by-user")
    public ResponseEntity<?> getByUser(@Valid SearchBasketItemListDTO SearchBasketItemDTO) {
        Page<BasketItemVO> basketItemsByUser = basketService.getByUser(SearchBasketItemDTO);
        return new ResponseEntity<>(basketItemsByUser,HttpStatus.OK);
    }

    // 장바구니 상품 추가
    @PostMapping(value = "/basket-item",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addBasket(@RequestBody @Valid AddBasketItemDTO addBasketItemDTO) {
        basketService.addBasketItem(addBasketItemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장바구니 상품 갯수 수정
    @PutMapping(value = "/basket-items/{basket-item-id}/count" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateBasket(@RequestBody @Valid UpdateBasketItemDTO updateBasketItemDTO,
                                             @PathVariable(value = "basket-item-id") Long basketItemId) {
        basketService.updateBasketItem(updateBasketItemDTO, basketItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장바구니 상품 삭제
    @DeleteMapping(value = "/basket-item" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteBasket(@RequestBody @Valid DeleteBasketItemDTO deleteBasketDTO) {
        basketService.deleteBasketItem(deleteBasketDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}