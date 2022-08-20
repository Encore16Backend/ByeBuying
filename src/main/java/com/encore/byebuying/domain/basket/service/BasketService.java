package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.dto.CreateBasketItemDTO;
import com.encore.byebuying.domain.basket.dto.DeleteBasketItemListDTO;
import com.encore.byebuying.domain.basket.dto.SearchBasketItemListDTO;
import com.encore.byebuying.domain.basket.dto.UpdateBasketItemDTO;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.basket.service.vo.BasketItemVO;
import com.encore.byebuying.domain.basket.service.vo.SearchBasketItemListParam;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.User;
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

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Page<BasketItemVO> getBasketItemList(SearchBasketItemListDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(RuntimeException::new);
        Basket basket = user.getBasket(); // 유저가 존재하면 바스켓은 존재
        Long basketId = basket.getId(); // basketId 널체크를 위해 basket과 분리
        SearchBasketItemListParam param = SearchBasketItemListParam.valueOf(dto, basketId);
        return basketItemRepository.findAll(param, dto.getPageRequest());
    }

    public void createBasketItem(CreateBasketItemDTO dto) {
        User findUser = userRepository.findById(dto.getUserId())
                .orElseThrow(RuntimeException::new);
        Item findItem = itemRepository.findById(dto.getItemId())
                .orElseThrow(RuntimeException::new);
        BasketItem basketItem = BasketItem.createBasketItem()
                .item(findItem)
                .count(dto.getCount())
                .basket(findUser.getBasket()).build();
        basketItemRepository.save(basketItem);
    }

    public void updateBasketItemCount(UpdateBasketItemDTO dto, Long basketItemId) {
        int count = dto.getCount();
        BasketItem findBasketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(RuntimeException::new);
        findBasketItem.setCount(count);
    }

    public void deleteBasketItemList(DeleteBasketItemListDTO dto) {
        List<Long> basketItemIds = dto.getBasketItemIds();
        basketItemIds.forEach(basketId -> basketItemRepository.deleteById(basketId));
    }

}
