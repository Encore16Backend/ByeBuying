package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.dto.*;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.common.paging.PagingResponse;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BasketServiceImpl implements BasketService{

    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BasketItemRepository basketItemRepository;

    private final EntityManager em;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @param pageable, username
     * 페이징해서 장바구니 아이템을 조회
     * 
     * */
    @Override
    public PagingResponse<BasketItem, BasketItemResponseDTO> getByUser(Pageable pageable, Long id) {
        Long basket_id = userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getBasket().getId();
        Page<BasketItem> basketItems = basketItemRepository.findByBasketId(pageable, basket_id);
        return new PagingResponse<>(new BasketItemResponseDTO(), basketItems);
    }

    /**
     * @Param
     * 장바구니에서 아이템 검색 (like)
     * */
    @Override
    public PagingResponse<BasketItem, BasketItemResponseDTO> getByItemName(Pageable pageable, BasketItemSearchDTO basketItemSearchDTO) {
        User findUser = userRepository.findById(basketItemSearchDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        Long basket_id = findUser.getBasket().getId();
        Page<BasketItem> basketItems = basketItemRepository.findByBasketIdAndItem_NameLike(pageable, basket_id, basketItemSearchDTO.getItemName());
        return new PagingResponse<>(new BasketItemResponseDTO(), basketItems);
    }

    /**
     * @param basketUpdateDTO
     * 장바구니 상품 갯수 수정
     * */
    @Transactional
    @Override
    public void updateBasketItem(BasketUpdateDTO basketUpdateDTO) {

        Long BasketItemId = basketUpdateDTO.getBasketItemId();
        int count = basketUpdateDTO.getCount();

        BasketItem findBasketItem = basketItemRepository.findById(BasketItemId).orElseThrow(EntityNotFoundException::new);
        findBasketItem.setCount(count);
    }

    /**
     * @param basketAddDTO
     * Long user_id,Long item_id,int count
     * 장바구니에 상품 추가
     * */
    @Transactional
    @Override
    public void addBasketItem(BasketItemAddDTO basketAddDTO) {

        Long user_id = basketAddDTO.getUserId();
        Long item_id = basketAddDTO.getItemId();
        int count = basketAddDTO.getCount();

        User findUser = userRepository.findById(user_id).orElseThrow(()-> {throw new NullPointerException();});
        Item findItem = itemRepository.findById(item_id).orElseThrow(()-> {throw new NullPointerException();});
        BasketItem basketItem = BasketItem.createBasketItem().item(findItem).count(count).build();
        basketItemRepository.save(basketItem);
        findUser.getBasket().addBasketItem(basketItem);
    }


    /**
     * @param basketDeleteDTO
     * Long user_id,Long item_id,int count
     * 장바구니 상품 삭제
     * */
    @Transactional
    @Override
    public void deleteBasketItem(BasketItemDeleteDTO basketDeleteDTO) {
        Long user_id = basketDeleteDTO.getUserId();
        List<Long> item_ids = basketDeleteDTO.getItemIds();
        User findUser = userRepository.findById(user_id).orElseThrow(()-> {throw new NullPointerException();});

        List<BasketItem> basket = findUser.getBasket().getBasketItems();
        for (Long id : item_ids){
            basket.removeIf(bItem -> (bItem.getItem().getId() == id) );
        }
    }




}