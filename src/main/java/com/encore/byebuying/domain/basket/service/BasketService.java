package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.service.vo.SearchBasketItemListParam;
import com.encore.byebuying.domain.basket.service.vo.BasketItemVO;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BasketService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BasketItemRepository basketItemRepository;

    /**
     * 장바구니 조회
     */
    public Page<BasketItemVO> getByUser(SearchBasketItemListDTO dto) {
         User user = userRepository.findById(dto.getUserId())
                 .orElseThrow(RuntimeException::new);
         Basket basket = user.getBasket(); // 유저가 존재하면 바스켓은 존재
         Long basketId = basket.getId(); // basketId 널체크를 위해 basket과 분리
         SearchBasketItemListParam param = SearchBasketItemListParam.valueOf(dto, basketId);
         return basketItemRepository.findAll(param, dto.getPageRequest());
    }

    /**
     * 장바구니 상품 갯수 수정
     * */
    @Transactional
    public void updateBasketItem(UpdateBasketItemDTO basketUpdateDTO, Long basketItemId) {
        int count = basketUpdateDTO.getCount();
        BasketItem findBasketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(RuntimeException::new);
        findBasketItem.setCount(count);
    }

    /**
     *  장바구니 상품 추가
     * */
    @Transactional
    public void addBasketItem(CreateBasketItemDTO CreateBasketItemDTO) {
        User findUser = userRepository.findById(CreateBasketItemDTO.getUserId())
                .orElseThrow(RuntimeException::new);
        Item findItem = itemRepository.findById(CreateBasketItemDTO.getItemId())
                .orElseThrow(RuntimeException::new);
        BasketItem basketItem = BasketItem.createBasketItem()
                .item(findItem)
                .count(CreateBasketItemDTO.getCount())
                .basket(findUser.getBasket()).build();
        basketItemRepository.save(basketItem);
    }

    /**
     * 장바구니 상품 삭제
     * */
    @Transactional
    public void deleteBasketItem(DeleteBasketItemDTO deleteBasketItemDTO) {
        List<Long> basketItemIds = deleteBasketItemDTO.getBasketItemIds();
        basketItemIds.forEach(basketId -> basketItemRepository.deleteById(basketId));
    }

}