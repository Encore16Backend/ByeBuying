package com.encore.byebuying.domain.basket.controller;

import com.encore.byebuying.domain.basket.BasketAndItem;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.service.BasketService;
import com.encore.byebuying.domain.common.paging.PagingResponse;
import com.encore.byebuying.domain.inquiry.dto.InquiryGetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    // 유저별 장바구니 상품 조회
    @GetMapping("/by-user")
    public ResponseEntity<?> getBasketByUser(@Valid BasketGetDTO basketGetDTO) {
        PagingResponse<BasketAndItem, BasketItemResponseDTO> basketItemsByUser = basketService.getByUser(basketGetDTO.getPageRequest(), basketGetDTO.getUserId());
        return new ResponseEntity<>(basketItemsByUser,HttpStatus.OK);
    }

    // 상품이름으로 장바구니 검색
    @GetMapping("/by-itemname")
    public ResponseEntity<?> getBasketItemByItemName(@Valid BasketItemSearchDTO dto) {
        PagingResponse<BasketAndItem, BasketItemResponseDTO> findItem = basketService.getByItemName(dto.getPageRequest(), dto);
        return new ResponseEntity<>(findItem,HttpStatus.OK);
    }

    // 장바구니 상품 추가
    @PostMapping(value = "/basket-item",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBasket(@RequestBody @Valid BasketItemAddDTO basketAddDTO) {
        basketService.addBasketItem(basketAddDTO);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    // 장바구니 상품 갯수 수정
    @PutMapping(value = "/count" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBasket(@RequestBody @Valid BasketUpdateDTO basketUpdateDTO) {
        basketService.updateBasketItem(basketUpdateDTO);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    // 장바구니 상품 삭제
    @DeleteMapping(value = "/{basket-id}/basket-items" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBasket(@RequestBody @Valid BasketItemDeleteDTO basketDeleteDTO) {
        // item이 basket안에 들어있는지 확인 하기위해 item_id 확인
        basketService.deleteBasketItem(basketDeleteDTO);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

}