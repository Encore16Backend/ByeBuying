package com.encore.byebuying.domain;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InquiryRepositoryTest {


    @Autowired
    InquiryRepository inquiryRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BasketRepository basketRepository;

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
                .name("상품")
                .price(1000)
                .stockQuantity(10)
                .build();
        return itemRepository.save(item);
    }

    @Test
    public void save(){
        User user = givenUser();
        Item item = givenItem();

        Inquiry inquiry = Inquiry.createInquiry(user, "title", "content");
        Inquiry inquiry1 = inquiryRepository.save(inquiry);

        entityManager.flush();
        entityManager.clear();

        assertThat(inquiry.getId()).isEqualTo(inquiry1.getId());
    }

}
