package com.encore.byebuying.service;

import com.encore.byebuying.domain.*;
import com.encore.byebuying.repo.*;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class InquiryServiceImplTest {


    @Autowired
    private BasketServiceImpl basketServiceImpl;
    @Autowired private UserRepo userRepo;
    @Autowired private ItemRepository itemRepository;
    @Autowired
    private BasketRepo basketRepo;
    @Autowired
    private BasketItemRepo basketItemRepo;
    @Autowired
    private InquiryServiceImpl inquiryService;
    @Autowired
    private InquiryRepo inquiryRepo;

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
    public void createInquiryTest(){
        User user = givenUser();
        Item item = givenItem();
        entityManager.flush();
        entityManager.clear();

        Inquiry inquiry = Inquiry.createInquiry(user, item, "상품사이즈가 작아요", "큰 사이즈로 바꿔주세요");

        inquiryRepo.save(inquiry);
        entityManager.flush();
        entityManager.clear();

        PageRequest pageRequest = PageRequest.of(1-1, 5);
        // 아이템 명 검색
        Page<Inquiry> inquiries = inquiryService.getByItemName(pageRequest, item.getName());
        System.out.println(inquiries.getContent());
        // 유저id 검색
        Page<Inquiry> inquiries2 = inquiryService.getByUserId(pageRequest, user.getId());

        assertThat(inquiries.getContent().get(0).getContent()).isEqualTo("큰 사이즈로 바꿔주세요");
        assertThat(inquiries2.getContent().get(0).getId()).isEqualTo(inquiry.getId());
    }
}
