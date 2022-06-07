package com.encore.byebuying.service;

import com.encore.byebuying.api.dto.basket.BasketAddRequest;
import com.encore.byebuying.api.dto.basket.BasketDeleteRequest;
import com.encore.byebuying.api.dto.basket.BasketUpdateRequest;
import com.encore.byebuying.domain.*;
import com.encore.byebuying.repo.BasketItemRepo;
import com.encore.byebuying.repo.BasketRepo;
import com.encore.byebuying.repo.ItemRepository;
import com.encore.byebuying.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BasketServiceImpl implements BasketService{

    private final BasketRepo basketRepo;
    private final ItemRepository itemRepository;
    private final UserRepo userRepo;
    private final BasketItemRepo basketItemRepo;

    private final EntityManager em;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public Page<BasketItem> findById(Pageable pageable, Long user_id) {
        // user_id로 basket_id를 찾은 다음 조회
        User user = userRepo.getById(user_id);
        return null;
    }

    /**
     * @param basketUpdateRequest
     * Long user_id,Long item_id,int count
     * 장바구니 상품 갯수 수정
     * */
    @Override
    public void updateBasketItem(BasketUpdateRequest basketUpdateRequest) {

        Long user_id = basketUpdateRequest.getUser_id();
        Long item_id = basketUpdateRequest.getItem_id();
        int count = basketUpdateRequest.getCount();

        User findUser = userRepo.findById(user_id).orElse(User.builder().build());
        Item findItem = itemRepository.findById(item_id).orElse(Item.builder().build());
        BasketItem basketItem = BasketItem.createBasketItem(findItem, count);
        basketItem.setCount(count);
        findUser.getBasket().updateBasketItem(basketItem);
    }

    /**
     * @param basketAddRequest
     * Long user_id,Long item_id,int count
     * 장바구니에 상품 추가
     * */
    @Override
    public void addBasketItem(BasketAddRequest basketAddRequest) {

        Long user_id = basketAddRequest.getUser_id();
        Long item_id = basketAddRequest.getItem_id();
        int count = basketAddRequest.getCount();

        User findUser = userRepo.findById(user_id).orElse(User.builder().build());
        Item findItem = itemRepository.findById(item_id).orElse(Item.builder().build());
        BasketItem basketItem = BasketItem.createBasketItem(findItem, count);
        basketItemRepo.save(basketItem);
        findUser.getBasket().addBasketItem(basketItem);
    }

    @Override
    public void deleteBasketItem(BasketDeleteRequest basketDeleteRequest) {
        Long user_id = basketDeleteRequest.getUser_id();
        Long item_id = basketDeleteRequest.getItem_id();

        User findUser = userRepo.findById(user_id).orElse(User.builder().build());
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
