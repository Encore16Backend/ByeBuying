package com.encore.byebuying.service;

import com.encore.byebuying.domain.*;
import com.encore.byebuying.repo.BasketItemRepo;
import com.encore.byebuying.repo.BasketRepo;
import com.encore.byebuying.repo.ItemRepository;
import com.encore.byebuying.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BasketServiceImplTest {

    @Autowired
    private BasketServiceImpl basketServiceImpl;
    @Autowired private UserRepo userRepo;
    @Autowired private ItemRepository itemRepository;
    @Autowired
    private BasketRepo basketRepo;
    @Autowired
    private BasketItemRepo basketItemRepo;

    @PersistenceContext
    private EntityManager entityManager;


    public User givenUser(){
        Basket basket = Basket.builder().id(1L)
                .basketItems(new ArrayList<>()).build();

        basketRepo.save(basket);
        User user = User.builder()
                .username("유저1")
                .password("1111")
                .email("test@naver.com")
                .basket(basket)
                .role(Role.USER)
                .build();
        return userRepo.save(user);
    }

    public Item givenItem(){
        Item item = Item.builder()
                .id(1L)
                .name("상품")
                .price(1000)
                .stockQuantity(10)
                .build();
        return itemRepository.save(item);
    }
    
    @Test
    public void test(){
        Basket basket = Basket.builder().id(1L).build();
        basketRepo.save(basket);

        entityManager.flush();
        entityManager.clear();

        User user = User.builder()
                .username("유저1")
                .password("1111")
                .email("test@naver.com")
                .basket(basket)
                .role(Role.USER)
                .build();
        userRepo.save(user);

        entityManager.flush();
        entityManager.clear();

        User findUser = userRepo.getById(1L);

        System.out.println("user :" + user);
        System.out.println("findUser = " + findUser);
        
        assertThat(user.getId()).isEqualTo(findUser.getId());
    }

    /***
     * param : String username, Long item_id
     *
     */
    @Test
    public void createBasketItem() throws Exception{
        // given
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();
        BasketItem basketItem = BasketItem.createBasketItem(item, 5);
        basketItemRepo.save(basketItem);
        entityManager.flush();
        entityManager.clear();

        // when
        // 유저 장바구니에 add
        user.getBasket().addBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();

        System.out.println("user.basket.getId = " + user.getBasket().getId());
        System.out.println("basketItem.getBasket().getId() = " + basketItem.getBasket().getId());

        List<BasketItem> lists  = user.getBasket().getBasketItems();
        for (int i = 0; i < lists.size(); i ++){
            System.out.println("item : " + lists.get(i).getItem());
        }

        // then
        assertThat(user.getBasket().getId()).isEqualTo(basketItem.getBasket().getId());
    }

    @Test
    public void deleteBasketItem(){
        // given
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();
        BasketItem basketItem = BasketItem.createBasketItem(item, 5);
        basketItemRepo.save(basketItem);
        entityManager.flush();
        entityManager.clear();
        // 유저 장바구니에 add
        user.getBasket().addBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();


        // when
        user.getBasket().deleteBasketItem(item.getId());
        entityManager.flush();
        entityManager.clear();

        // then
        assertThat(user.getBasket().getBasketItems().size()).isEqualTo(0);
    }

    @Test
    public void updateBasketItem(){
        // given
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();
        BasketItem basketItem = BasketItem.createBasketItem(item, 5);
        basketItemRepo.save(basketItem);
        entityManager.flush();
        entityManager.clear();
        // 유저 장바구니에 add
        user.getBasket().addBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();

        // 수량을 변경
        basketItem.setCount(10);
        entityManager.flush();
        entityManager.clear();

        user.getBasket().updateBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();

        System.out.println("user.getBasket().getBasketItems().get(0).getCount() = " + user.getBasket().getBasketItems().get(0).getCount());
        // then
        assertThat(user.getBasket().getBasketItems().get(0).getCount()).isEqualTo(10);
    }


    @Test
    public void BasketItemPaging(){
        User user = givenUser();

        Item item1 = Item.builder().id(1L).name("상품1").price(1000).stockQuantity(10).build();
        Item item2 = Item.builder().id(2L).name("상품2").price(1000).stockQuantity(10).build();
        Item item3 = Item.builder().id(3L).name("상품3").price(1000).stockQuantity(10).build();
        Item item4 = Item.builder().id(4L).name("상품4").price(1000).stockQuantity(10).build();
        Item item5 = Item.builder().id(5L).name("상품5").price(1000).stockQuantity(10).build();

        itemRepository.saveAll(Arrays.asList(item1, item2, item3, item4, item5));

        BasketItem basketItem1 = BasketItem.createBasketItem(item1, 5);
        BasketItem basketItem2 = BasketItem.createBasketItem(item2, 5);
        BasketItem basketItem3 = BasketItem.createBasketItem(item3, 5);
        BasketItem basketItem4 = BasketItem.createBasketItem(item4, 5);
        BasketItem basketItem5 = BasketItem.createBasketItem(item5, 5);

        basketItemRepo.saveAll(Arrays.asList(basketItem1, basketItem2, basketItem3, basketItem4, basketItem5));

        Basket userBasket = user.getBasket();

        userBasket.addBasketItem(basketItem1);
        userBasket.addBasketItem(basketItem2);
        userBasket.addBasketItem(basketItem3);
        userBasket.addBasketItem(basketItem4);
        userBasket.addBasketItem(basketItem5);

        entityManager.flush();
        entityManager.clear();

        PageRequest pageRequest = PageRequest.of(1, 2);

        Page<BasketItem> basketItems = basketServiceImpl.findByUserId(pageRequest, user.getId());


        System.out.println("basketItems.getContent() = " + basketItems.getContent());
        System.out.println("basketItems.getTotalElements() = " + basketItems.getTotalElements());
        System.out.println("basketItems.getTotalPages() = " + basketItems.getTotalPages());

    }
}
