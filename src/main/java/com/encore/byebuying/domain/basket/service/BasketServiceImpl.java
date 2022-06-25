package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.dto.BasketAddDTO;
import com.encore.byebuying.domain.basket.dto.BasketDeleteDTO;
import com.encore.byebuying.domain.basket.dto.BasketUpdateDTO;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BasketServiceImpl implements BasketService{

    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BasketItemRepository basketItemRepository;

    private final EntityManager em;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 페이징해서 장바구니 아이템을 리턴
     * */
    @Override
    public Page<BasketItem> findById(Pageable pageable, Long user_id) {
        // user_id로 basket_id를 찾은 다음 조회
        Long basket_id = userRepository.getById(user_id).getBasket().getId();
        return basketItemRepository.findByBasketId(pageable, basket_id);
    }

    /**
     * @param basketUpdateDTO
     * 장바구니 상품 갯수 수정
     * */
    @Override
    public void updateBasketItem(BasketUpdateDTO basketUpdateDTO) {

        Long user_id = basketUpdateDTO.getUser_id();
        List<Long> item_ids = basketUpdateDTO.getItem_ids();
        List<Integer> counts = basketUpdateDTO.getCounts();

        User findUser = userRepository.findById(user_id).orElseThrow(()-> {throw new NullPointerException();});

        List<BasketItem> basket = findUser.getBasket().getBasketItems();
        for (Long id : item_ids){
            int i = 0;
            for (BasketItem bItme : basket){
                if (bItme.getItem().getId() == id) bItme.setCount(counts.get(i));
            }
            i++;
        }
    }

    /**
     * @param basketAddDTO
     * Long user_id,Long item_id,int count
     * 장바구니에 상품 추가
     * */
    @Override
    public void addBasketItem(BasketAddDTO basketAddDTO) {

        Long user_id = basketAddDTO.getUser_id();
        Long item_id = basketAddDTO.getItem_id();
        int count = basketAddDTO.getCount();

        User findUser = userRepository.findById(user_id).orElseThrow(()-> {throw new NullPointerException();});
        Item findItem = itemRepository.findById(item_id).orElseThrow(()-> {throw new NullPointerException();});
        BasketItem basketItem = BasketItem.createBasketItem(findItem, count);
        basketItemRepository.save(basketItem);
        findUser.getBasket().addBasketItem(basketItem);
    }

    @Override
    public void deleteBasketItem(BasketDeleteDTO basketDeleteDTO) {
        Long user_id = basketDeleteDTO.getUser_id();
        List<Long> item_ids = basketDeleteDTO.getItem_ids();
        User findUser = userRepository.findById(user_id).orElseThrow(()-> {throw new NullPointerException();});

        List<BasketItem> basket = findUser.getBasket().getBasketItems();
        for (Long id : item_ids){
            basket.removeIf(bItem -> (bItem.getItem().getId() == id) );
        }
    }




}
