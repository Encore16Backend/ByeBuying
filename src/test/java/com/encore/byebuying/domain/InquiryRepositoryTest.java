package com.encore.byebuying.domain;

import com.encore.byebuying.repo.BasketRepo;
import com.encore.byebuying.repo.InquiryRepo;
import com.encore.byebuying.repo.ItemRepository;
import com.encore.byebuying.repo.UserRepo;
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
    InquiryRepo inquiryRepo;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepo userRepo;
    @Autowired
    BasketRepo basketRepo;

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

        Inquiry inquiry = Inquiry.createInquiry(user, item, "title", "content");
        Inquiry inquiry1 = inquiryRepo.save(inquiry);

        entityManager.flush();
        entityManager.clear();

        String findInquiryContent = item.getInquiries().get(0).getContent();
        System.out.println("findInquiryContent = " + findInquiryContent);
        assertThat(inquiry.getId()).isEqualTo(inquiry1.getId());
    }

}
