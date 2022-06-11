package com.encore.byebuying.domain;

import com.encore.byebuying.repo.BasketRepo;
import com.encore.byebuying.repo.ItemRepository;
import com.encore.byebuying.repo.UserRepo;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BasketRepositoryTest {


    @Autowired
    BasketRepo basketRepo;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepo userRepo;

    @Test
    public void createItem(){

        Item item = Item.builder()
                .name("상품")
                .price(1000)
                .stockQuantity(10)
                .build();
        // when
        Item saveItem = itemRepository.save(item);
        // then
        assertThat(saveItem).isNotNull();
        assertThat(saveItem.getId()).isEqualTo(item.getId());
        assertThat(saveItem.getPrice()).isEqualTo(1000);
        assertThat(saveItem.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @Transactional
    public void createUser(){
        User user = User.builder()
                .username("유저1")
                .email("test@naver.com")
                .password("1111")
                .basket(new Basket())
                .role(Role.USER)
                .build();
        userRepo.save(user);

        User findUser = userRepo.getById(1L);

        assertThat(user.getId()).isEqualTo(findUser.getId());

    }



    public User givenUser(){
        User user = User.builder()
                .username("유저1")
                .email("test@naver.com")
                .password("1111")
                .basket(new Basket())
                .role(Role.USER)
                .build();
        return userRepo.save(user);
    }

    public Item givenItem(){
        Item item = Item.builder()
                .name("상품")
                .price(1000)
                .stockQuantity(10)
                .build();
        return itemRepository.save(item);
    }



    @Test
    @Transactional
    public void addItemToBasket(){
        Item item = givenItem();
        User user = givenUser();
        // 장바구니 아이템으로 만든다.
        BasketItem basketItem = BasketItem.createBasketItem(item, 5);

        // 유저 장바구니에 추가
        user.getBasket().addBasketItem(basketItem);
        
        // 유저에서 확인
        BasketItem findBasketItem =  user.getBasket().getBasketItems().get(0);

        assertThat(basketItem.getId()).isEqualTo(findBasketItem.getId());
    }

}
