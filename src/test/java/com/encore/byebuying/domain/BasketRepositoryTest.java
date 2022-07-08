package com.encore.byebuying.domain;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BasketRepositoryTest {


    @Autowired
    BasketRepository basketRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;



//    @Test
//    public void createItem(){
//
//        Item item = Item.builder()
//                .name("상품")
//                .price(1000)
//                .stockQuantity(10)
//                .build();
//        // when
//        Item saveItem = itemRepository.save(item);
//        // then
//        assertThat(saveItem).isNotNull();
//        assertThat(saveItem.getId()).isEqualTo(item.getId());
//        assertThat(saveItem.getPrice()).isEqualTo(1000);
//        assertThat(saveItem.getStockQuantity()).isEqualTo(10);
//    }

    @Test
    @Transactional
    public void createUser(){

//        private String username;
//        private String password;
//        private String email;
//        private int defaultLocationIdx;
//        private Collection<Location> locations;


    }



//    public User givenUser(){
//        Basket basket = Basket.initBasket().build();
//        basketRepository.save(basket);
//        User user = User.builder()
//                .username("유저1")
//                .password("1111")
//                .email("test@naver.com")
//                .basket(basket)
//                .roleType(RoleType.USER)
//                .build();
//        return userRepository.save(user);
//    }

//    public Item givenItem(){
//        Item item = Item.builder()
//                .name("상품")
//                .price(1000)
//                .stockQuantity(10)
//                .build();
//        return itemRepository.save(item);
//    }



//    @Test
//    @Transactional
//    public void addItemToBasket(){
//        Item item = givenItem();
//        User user = givenUser();
//        // 장바구니 아이템으로 만든다.
//        BasketItem basketItem = BasketItem.createBasketItem(item, 5);
//
//        // 유저 장바구니에 추가
//        user.getBasket().addBasketItem(basketItem);
//
//        // 유저에서 확인
//        BasketItem findBasketItem =  user.getBasket().getBasketItems().get(0);
//
//        assertThat(basketItem.getId()).isEqualTo(findBasketItem.getId());
//    }
}