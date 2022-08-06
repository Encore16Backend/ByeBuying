package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;

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
    public Page<BasketItem> findByUserId(Pageable pageable, Long user_id) {
        // user_id로 basket_id를 찾은 다음 조회
        User user = userRepository.getById(user_id);
        Long basket_id = user.getBasket().getId();
        return basketItemRepository.findByBasketId(pageable, basket_id);
    }

    /**
     * @param basketUpdateDTO
     * Long user_id,Long item_id,int count
     * 장바구니 상품 갯수 수정
     * */
    @Override
    public void updateBasketItem(BasketUpdateDTO basketUpdateDTO) {

        Long user_id = basketUpdateDTO.getUser_id();
        Long item_id = basketUpdateDTO.getItem_id();
        int count = basketUpdateDTO.getCount();

        User findUser = userRepository.findById(user_id).orElse(User.builder().build());
        Item findItem = itemRepository.findById(item_id).orElse(Item.builder().build());
        BasketItem basketItem = BasketItem.createBasketItem(findItem, count);
//        basketItem.setCount(count);
        findUser.getBasket().updateBasketItem(basketItem);
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

        User findUser = userRepository.findById(user_id).orElse(User.builder().build());
        Item findItem = itemRepository.findById(item_id).orElse(Item.builder().build());
        BasketItem basketItem = BasketItem.createBasketItem(findItem, count);
        basketItemRepository.save(basketItem);
        findUser.getBasket().addBasketItem(basketItem);
    }

    @Override
    public void deleteBasketItem(BasketDeleteDTO basketDeleteDTO) {
        Long user_id = basketDeleteDTO.getUser_id();
        Long item_id = basketDeleteDTO.getItem_id();

        User findUser = userRepository.findById(user_id).orElse(User.builder().build());
        findUser.getBasket().deleteBasketItem(item_id);
    }


//    @Override
//    public Basket saveBasket(Basket basket, String mode) {
//        if (mode.equals("save")){
//            Basket checkBasket = basketRepo.findBasketByUsernameAndItemid(basket.getUsername(), basket.getItemid());
//            if(checkBasket != null){
//                log.info("Save Mode, Basket Exist");
//                checkBasket.setBcount(checkBasket.getBcount()+basket.getBcount());
//                return basketRepo.save(checkBasket);
//            }
//            log.info("Save Mode, Basket Not Exist");
//            return basketRepo.save(basket);
//        }
//        log.info("Update Mode");
//        return basketRepo.save(basket);
//    }
//
//    @Override
//    public Page<Basket> getByUsername(Pageable pageable, String username) {
//        return basketRepo.findByUsername(pageable, username);
//    }
//
//    @Override
//    public Basket getBasketById(Long id) {
//        return basketRepo.findBasketById(id);
//    }
//
//    @Override
//    public void deleteBasket(Long id) {
//        log.info("Delete Basket By id {}", id);
//        basketRepo.deleteById(id);
//    }
//
//    @Override
//    public void deleteBasketByItemidAndUsername(Long itemid, String username) {
//        log.info("Delete Basket By Buy Item, itemid: {}, username: {}", itemid, username);
//        basketRepo.deleteByItemidAndUsername(itemid, username);
//    }


}
