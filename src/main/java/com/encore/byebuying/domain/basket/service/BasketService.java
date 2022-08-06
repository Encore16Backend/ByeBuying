package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.service.vo.BasketItemResponseVO;
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
     * @param basketItemSearchDTO
     *
     *
     * @return*/
    public Page<BasketItemResponseVO> getByUser(BasketItemSearchDTO basketItemSearchDTO) {
         Long basketId = userRepository.getById(basketItemSearchDTO.getUserId()).getBasket().getId();
         return basketItemRepository.getByUser(basketItemSearchDTO, basketId);
    }

    /**
     * @param basketUpdateDTO
     * 장바구니 상품 갯수 수정
     * */
    @Transactional
    public void updateBasketItem(BasketUpdateDTO basketUpdateDTO) {
        Long BasketItemId = basketUpdateDTO.getBasketItemId();
        int count = basketUpdateDTO.getCount();
        BasketItem findBasketItem = basketItemRepository.findById(BasketItemId).orElseThrow(RuntimeException::new);
        findBasketItem.setCount(count);
    }

    /**
     * @param basketAddDTO
     * Long user_id,Long item_id,int count
     * 장바구니에 상품 추가
     * */
    @Transactional
    public void addBasketItem(BasketItemAddDTO basketAddDTO) {
        User findUser = userRepository.findById(basketAddDTO.getUserId()).orElseThrow(RuntimeException::new);
        Item findItem = itemRepository.findById(basketAddDTO.getItemId()).orElseThrow(RuntimeException::new);
        BasketItem basketItem = BasketItem.createBasketItem()
                .item(findItem)
                .count(basketAddDTO.getCount())
                .basket(findUser.getBasket()).build();
        basketItemRepository.save(basketItem);
    }

    /**
     * @param basketDeleteDTO
     * Long user_id,Long item_id,int count
     * 장바구니 상품 삭제
     * */
    @Transactional
    public void deleteBasketItem(BasketItemDeleteDTO basketDeleteDTO) {
        List<Long> basketItemIds = basketDeleteDTO.getBasketItemIds();
        basketItemIds.forEach(basketId -> basketItemRepository.deleteById(basketId));
    }

    
}