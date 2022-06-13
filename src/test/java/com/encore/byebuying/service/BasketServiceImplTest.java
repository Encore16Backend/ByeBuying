package com.encore.byebuying.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.service.BasketServiceImpl;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Autowired private UserRepository userRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private BasketItemRepository basketItemRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public User givenUser(){
        Basket basket = Basket.builder().id(1L)
                .basketItems(new ArrayList<>()).build();

        basketRepository.save(basket);
        User user = User.builder()
                .username("유저1")
                .password("1111")
                .email("test@naver.com")
                .basket(basket)
                .roleType(RoleType.USER)
                .build();
        return userRepository.save(user);
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
        basketRepository.save(basket);

        entityManager.flush();
        entityManager.clear();

        User user = User.builder()
                .username("유저1")
                .password("1111")
                .email("test@naver.com")
                .basket(basket)
                .roleType(RoleType.USER)
                .build();
        userRepository.save(user);

        entityManager.flush();
        entityManager.clear();

        User findUser = userRepository.getById(1L);

        System.out.println("user :" + user);
        System.out.println("findUser = " + findUser);
        
        assertThat(user.getId()).isEqualTo(findUser.getId());
    }

    /***
     * param : String username, Long item_id
     *
     */
    @Test
    public void createBasket() throws Exception {
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();
        BasketItem basketItem = BasketItem.createBasketItem(item, 5);
        user.getBasket().addBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();
        System.out.println(user.getBasket().getBasketItems().get(0).getItem().getName());
        System.out.println(user.getBasket().getBasketItems().get(0).getCount());
        assertThat(user.getBasket().getBasketItems().get(0).getItem().getName()).isEqualTo("상품");
    }

    @Test
    public void deleteBasketItem(){
        // given
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();
        BasketItem basketItem = BasketItem.createBasketItem(item, 5);
        user.getBasket().addBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();

        System.out.println(user.getBasket().getBasketItems().size() + ": user.getBasket().getBasketItems().size()");

        List<Long> ids = new ArrayList<>();
        ids.add(item.getId());

        List<BasketItem> basket = user.getBasket().getBasketItems();
        for (Long id : ids){
            basket.removeIf(bItem -> (bItem.getItem().getId() == id) );
        }

        entityManager.flush();
        entityManager.clear();

        System.out.println(user.getBasket().getBasketItems().size() + ": user.getBasket().getBasketItems().size()2");
        // then
        assertThat(user.getBasket().getBasketItems().size()).isEqualTo(0);
    }

    @Test
    public void updateBasketItem(){ // 해야됨
        // given
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();
        BasketItem basketItem = BasketItem.createBasketItem(item, 5);
        basketItemRepository.save(basketItem);
        entityManager.flush();
        entityManager.clear();
        // 유저 장바구니에 add
        user.getBasket().addBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();

        // 수량을 변경
//        basketItem.setCount(10);
        entityManager.flush();
        entityManager.clear();

//        user.getBasket().updateBasketItem(basketItem);
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

        basketItemRepository.saveAll(Arrays.asList(basketItem1, basketItem2, basketItem3, basketItem4, basketItem5));

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
