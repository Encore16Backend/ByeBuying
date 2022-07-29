package com.encore.byebuying.domain.basket.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketAndItem;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.dto.BasketItemDeleteDTO;
import com.encore.byebuying.domain.basket.dto.BasketItemResponseDTO;
import com.encore.byebuying.domain.basket.dto.BasketItemSearchDTO;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.common.paging.PagingResponse;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketItemRepository;
import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.dto.UserDTO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.encore.byebuying.domain.code.ProviderType.GOOGLE;
import static com.encore.byebuying.domain.code.ProviderType.LOCAL;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BasketServiceImplTest {

    @Autowired private BasketServiceImpl basketServiceImpl;
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

        System.out.println(item1.getId());
        System.out.println(item2.getId());
        System.out.println(item3.getId());
        System.out.println(item4.getId());
        System.out.println(item5.getId());

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
        entityManager.flush();
        entityManager.clear();

        System.out.println(user.getBasket().getBasketItems().size() + ": user.getBasket().getBasketItems().size()");

        List<Long> ids = new ArrayList<>();
        ids.add(item.getId());

        BasketItemDeleteDTO basketItemDeleteDTO = new BasketItemDeleteDTO();
        basketItemDeleteDTO.setBasketItemIds(ids);

        basketServiceImpl.deleteBasketItem(basketItemDeleteDTO);

        entityManager.flush();
        entityManager.clear();

        System.out.println(user.getBasket().getBasketItems().size() + ": user.getBasket().getBasketItems().size()");

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


        int changeCnt = 1;

        System.out.println(user.getBasket().getBasketItems().size() + ": user.getBasket().getBasketItems().size()");

        BasketItem findBasketItem = basketItemRepository.findById(basketItem.getId()).orElseThrow(()-> {throw new NullPointerException();});
        findBasketItem.setCount(changeCnt);

        System.out.println("=============");
        System.out.println(user.getId());

        User findUser = userRepository.getById(user.getId());
        // then
        assertThat(findUser.getBasket().getBasketItems().get(0).getCount()).isEqualTo(changeCnt);
    }



    @Test
    public void BasketItemPaging() throws Exception {
        // given
        User user = givenUser();
        entityManager.flush();
        entityManager.clear();

        saveBasketItemsToUser(user);

        PageRequest byUserPaging = PageRequest.of(0, 5);
        User byUser = userRepository.getById(user.getId());

        var byUserBasketItems = basketItemRepository.findByBasketId(byUserPaging, byUser.getBasket().getId());
        PagingResponse pR = new PagingResponse(new BasketItemResponseDTO(), byUserBasketItems);

        System.out.println(pR.getContent());
        assertThat(pR.getTotalPage()).isEqualTo(1);

    }


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

        PageRequest byItemPaging = PageRequest.of(0, 5);

        var basketItemResponseDTO = basketServiceImpl.getByItemName(byItemPaging, basketItemSearchDTO);
        var byUserBasketItemsContent = basketItemResponseDTO.getContent();

        System.out.println(byUserBasketItemsContent);
        assertThat(byUserBasketItemsContent.get(0).getPrice()).isEqualTo(1000);
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
        basketItemSearchDTO.setUserId(1L);
        basketItemSearchDTO.setStartDate(LocalDateTime.of(2022,7,1,13,5));
        basketItemSearchDTO.setEndDate(LocalDateTime.now().minusDays(1L));

        PageRequest byItemPaging = PageRequest.of(0, 5);

        // 날짜포함 검색
        var basketItemResponse = basketItemRepository.findByBasketIdAndCreatedAtBetween(
                byItemPaging, user.getBasket().getId(), basketItemSearchDTO.getItemName() ,basketItemSearchDTO.getStartDate(), basketItemSearchDTO.getEndDate());

        PagingResponse pagingResponse = new PagingResponse(new BasketItemResponseDTO(), basketItemResponse);
        System.out.println(pagingResponse.getContent());

        assertThat(pagingResponse.getContent().size()).isEqualTo(0);


        // 날짜 없이 검색
        BasketItemSearchDTO basketItemSearchDTO2 = new BasketItemSearchDTO();
        basketItemSearchDTO2.setUserId(1L);

        var pagingResponse2 = basketServiceImpl.getByItemName(
                byItemPaging, basketItemSearchDTO2);

        System.out.println(pagingResponse2.getContent());

        assertThat(pagingResponse2.getContent().size()).isEqualTo(5);
        for (int i = 0; i < pagingResponse2.getContent().size(); i++){
            BasketItemResponseDTO item = pagingResponse2.getContent().get(i);
            assertThat(item.getPrice()).isEqualTo(1000);
        }

    }
}