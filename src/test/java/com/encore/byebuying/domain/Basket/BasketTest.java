package com.encore.byebuying.domain.Basket;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.dto.BasketItemDeleteDTO;
import com.encore.byebuying.domain.basket.service.BasketService;
import com.encore.byebuying.domain.basket.service.vo.BasketItemRequestVO;
import com.encore.byebuying.domain.basket.service.vo.BasketItemResponseVO;
import com.encore.byebuying.domain.basket.dto.BasketItemSearchDTO;
import com.encore.byebuying.domain.basket.dto.BasketUpdateDTO;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.encore.byebuying.domain.code.ProviderType.LOCAL;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class BasketTest {
    
    @Autowired private UserRepository userRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private BasketItemRepository basketItemRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public User givenUser() throws Exception {
        User user = userRepository.save(User.builder().username("test").roleType(RoleType.USER).provider(LOCAL).address(new Address("d","t","t")).password("1111").email("test@naver.com").basket(Basket.createBasket()).build());
        entityManager.flush();
        entityManager.clear();
        return user;
    }

    public void saveBasketItemsToUser(User user){
        Item item1 = Item.builder().name("상품1").price(1000).stockQuantity(10).build();
        Item item2 = Item.builder().name("상품2").price(1000).stockQuantity(10).build();
        Item item3 = Item.builder().name("상품3").price(1000).stockQuantity(10).build();
        Item item4 = Item.builder().name("상품4").price(1000).stockQuantity(10).build();
        Item item5 = Item.builder().name("상품5").price(1000).stockQuantity(10).build();

        itemRepository.saveAll(Arrays.asList(item1, item2, item3, item4, item5));
        entityManager.flush();
        entityManager.clear();

        BasketItem basketItem1 = BasketItem.createBasketItem().item(item1).count(5).basket(user.getBasket()).build();
        BasketItem basketItem2 = BasketItem.createBasketItem().item(item2).count(5).basket(user.getBasket()).build();
        BasketItem basketItem3 = BasketItem.createBasketItem().item(item3).count(5).basket(user.getBasket()).build();
        BasketItem basketItem4 = BasketItem.createBasketItem().item(item4).count(5).basket(user.getBasket()).build();
        BasketItem basketItem5 = BasketItem.createBasketItem().item(item5).count(5).basket(user.getBasket()).build();

        basketItemRepository.saveAll(Arrays.asList(basketItem1, basketItem2, basketItem3, basketItem4, basketItem5));

        entityManager.flush();
        entityManager.clear();

        Basket userBasket = user.getBasket();

        userBasket.addBasketItem(basketItem1);
        userBasket.addBasketItem(basketItem2);
        userBasket.addBasketItem(basketItem3);
        userBasket.addBasketItem(basketItem4);
        userBasket.addBasketItem(basketItem5);

        entityManager.flush();
        entityManager.clear();
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
        BasketItem basketItem = BasketItem.createBasketItem().item(item).count(5).basket(user.getBasket()).build();
        user.getBasket().addBasketItem(basketItem);
        entityManager.flush();
        entityManager.clear();
        System.out.println(user.getBasket().getBasketItems().get(0).getItem().getName());
        System.out.println(user.getBasket().getBasketItems().get(0).getCount());
        assertThat(user.getBasket().getBasketItems().get(0).getItem().getName()).isEqualTo("상품");
    }


    @Test
    public void deleteBasketItem() throws Exception {
        // given
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();
        BasketItem basketItem = BasketItem.createBasketItem().item(item).count(5).basket(user.getBasket()).build();
        user.getBasket().addBasketItem(basketItem);
        basketItemRepository.save(basketItem);

        List<Long> ids = new ArrayList<>();
        ids.add(basketItem.getId());

        ids.forEach(basketId -> basketItemRepository.deleteById(basketId));
        entityManager.flush();
        entityManager.clear();

        // then
        User user1 = userRepository.getById(user.getId());
        assertThat(user1.getBasket().getBasketItems().size()).isEqualTo(0);
    }

    @Test
    public void updateBasketItem() throws Exception {
        // given
        User user = givenUser();
        Item item = givenItem();

        BasketItem basketItem = BasketItem.createBasketItem().item(item).count(5).basket(user.getBasket()).build();
        basketItemRepository.save(basketItem);
        user.getBasket().addBasketItem(basketItem);

        // 업데이트
        BasketItem findBasketItem = basketItemRepository.findById(basketItem.getId()).orElseThrow(RuntimeException::new);
        findBasketItem.setCount(1);

        User findUser = userRepository.getById(user.getId());
        // then
        assertThat(findUser.getBasket().getBasketItems().get(0).getCount()).isEqualTo(1);
    }


    // 검색어 검색
    @Test
    public void BasketItemSearch() throws Exception {
        // given
        User user = givenUser();
        entityManager.flush();
        entityManager.clear();

        saveBasketItemsToUser(user);

        BasketItemSearchDTO basketItemSearchDTO = new BasketItemSearchDTO();
        basketItemSearchDTO.setUserId(user.getId());
        basketItemSearchDTO.setItemName("1");
        basketItemSearchDTO.setStartDate(LocalDateTime.now());

        Basket basket = user.getBasket(); // 유저가 존재하면 바스켓은 존재
        BasketItemRequestVO vo = BasketItemRequestVO.valueOf(basket);
        var basketItems = basketItemRepository.findAll(basketItemSearchDTO, vo);

//        System.out.println(basketItems.getContent());

        assertThat(basketItems.getContent().get(0).getItemName()).isEqualTo("상품1");
    }

    // 날짜, 유저 id 검색
    @Test
    public void BasketItemSearchDate() throws Exception {

        // given
        User user = givenUser();
        entityManager.flush();
        entityManager.clear();

        saveBasketItemsToUser(user);

        BasketItemSearchDTO basketItemSearchDTO = new BasketItemSearchDTO();
        basketItemSearchDTO.setUserId(user.getId());
        basketItemSearchDTO.setStartDate(LocalDateTime.of(2022,7,1,13,5));
        basketItemSearchDTO.setEndDate(LocalDateTime.now().minusDays(1L));

        // 날짜포함 검색
        Basket basket = user.getBasket(); // 유저가 존재하면 바스켓은 존재
        BasketItemRequestVO vo = BasketItemRequestVO.valueOf(basket);
        var basketItems = basketItemRepository.findAll(basketItemSearchDTO, vo);

        assertThat(basketItems.getContent().size()).isEqualTo(0);


        // 날짜 없이 검색
        BasketItemSearchDTO basketItemSearchDTO2 = new BasketItemSearchDTO();
        basketItemSearchDTO2.setUserId(user.getId());

        var basketItems2 = basketItemRepository.findAll(basketItemSearchDTO2, vo);

        assertThat(basketItems2.getContent().size()).isEqualTo(5);
        for (int i = 0; i < basketItems2.getContent().size(); i++){
            BasketItemResponseVO item = basketItems2.getContent().get(i);
            assertThat(item.getPrice()).isEqualTo(1000);
        }

    }
}