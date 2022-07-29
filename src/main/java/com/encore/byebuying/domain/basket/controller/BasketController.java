package com.encore.byebuying.domain.basket.controller;

import com.encore.byebuying.domain.basket.BasketAndItem;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.service.BasketService;
import com.encore.byebuying.domain.common.paging.PagingResponse;
import com.encore.byebuying.domain.inquiry.dto.InquiryGetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    // 유저별 장바구니 상품 조회
    @GetMapping("/by-user")
    public ResponseEntity<?> getBasketByUSer(@RequestBody @Valid BasketGetDTO basketGetDTO) {
        PagingResponse<BasketAndItem, BasketItemResponseDTO> basketItemsByUser = basketService.getByUser(basketGetDTO.getPageRequest(), basketGetDTO.getUserId());
        return new ResponseEntity<>(basketItemsByUser,HttpStatus.OK);
    }

    // 상품이름으로 장바구니 검색
    @GetMapping("/by-itemname")
    public ResponseEntity<?> getBasketItemByItemName(@RequestBody @Valid BasketItemSearchDTO dto) {
        PagingResponse<BasketAndItem, BasketItemResponseDTO> findItem = basketService.getByItemName(dto.getPageRequest(), dto);
        return new ResponseEntity<>(findItem,HttpStatus.OK);
    }

    // 장바구니 상품 추가
    @PostMapping
    public ResponseEntity<?> addBasket(@RequestBody @Valid BasketItemAddDTO basketAddDTO) {
        basketService.addBasketItem(basketAddDTO);
        PagingResponse<BasketAndItem, BasketItemResponseDTO> basketItemsByUser = basketService.getByUser(basketAddDTO.getPageRequest(), basketAddDTO.getUserId());
        return new ResponseEntity<>(basketItemsByUser,HttpStatus.OK);
    }

    // 장바구니 상품 갯수 수정
    @PostMapping("/update-count")
    public ResponseEntity<?> updateBasket(@RequestBody @Valid BasketUpdateDTO basketUpdateDTO) {
        basketService.updateBasketItem(basketUpdateDTO);
        PagingResponse<BasketAndItem, BasketItemResponseDTO> basketItemsByUser = basketService.getByUser(basketUpdateDTO.getPageRequest(), basketUpdateDTO.getUserId());
        return new ResponseEntity<>(basketItemsByUser,HttpStatus.OK);
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/removal-item")
    public ResponseEntity<?> deleteBasket(@RequestBody @Valid BasketItemDeleteDTO basketDeleteDTO) {
        basketService.deleteBasketItem(basketDeleteDTO);
        PagingResponse<BasketAndItem, BasketItemResponseDTO> basketItemsByUser = basketService.getByUser(basketDeleteDTO.getPageRequest(), basketDeleteDTO.getUserId());
        return new ResponseEntity<>(basketItemsByUser,HttpStatus.OK);
    }

}