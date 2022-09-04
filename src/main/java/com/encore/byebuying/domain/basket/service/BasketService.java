package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.dto.CreateBasketItemDTO;
import com.encore.byebuying.domain.basket.dto.DeleteBasketItemListDTO;
import com.encore.byebuying.domain.basket.dto.SearchBasketItemListDTO;
import com.encore.byebuying.domain.basket.dto.UpdateBasketItemDTO;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.basket.service.vo.BasketItemVO;
import com.encore.byebuying.domain.basket.service.vo.SearchBasketItemListParam;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.repository.user.UserRepository;
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

    private final BasketItemRepository basketItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Page<BasketItemVO> getBasketItemList(SearchBasketItemListDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        Basket basket = user.getBasket(); // 유저가 존재하면 바스켓은 존재

        SearchBasketItemListParam param = SearchBasketItemListParam.valueOf(dto, basket.getId());

        return basketItemRepository.findAll(param, dto.getPageRequest());
    }

    @Transactional
    public BasketItemVO createBasketItem(CreateBasketItemDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        Item findItem = itemRepository.findById(dto.getItemId())
                .orElseThrow(RuntimeException::new);

        BasketItem basketItem = BasketItem.createBasketItem()
                .item(findItem)
                .count(dto.getCount())
                .basket(user.getBasket()).build();
        basketItemRepository.save(basketItem);

        return BasketItemVO.valueOf(basketItem);
    }

    @Transactional
    public void updateBasketItemCount(UpdateBasketItemDTO dto, Long basketItemId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        Basket basket = user.getBasket();

        BasketItem basketItem = basketItemRepository
                .findByIdAndBasketId(basketItemId, basket.getId());
        basketItem.setCount(dto.getCount());

        basketItemRepository.save(basketItem);
    }

    @Transactional
    public void deleteBasketItemList(DeleteBasketItemListDTO dto, Long userId) {
        List<Long> basketItemIds = dto.getBasketItemIds();
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        Basket basket = user.getBasket();

        basketItemIds.forEach(basketItemId -> basketItemRepository.deleteByIdAndBasketId(basketItemId,basket.getId()));
    }

}
