package com.encore.byebuying.domain.review.service;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.review.Review;
import com.encore.byebuying.domain.review.repository.ReviewRepository;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceImplTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Test
    void save() {
        User user = givenUser("test용 user1");
        Item item = givenItem("test용 item");

        Review review = Review.builder()
                .user(user)
                .item(item)
                .score(5)
                .content("review save test").build();

        reviewRepository.save(review);
        List<Review> findReview = reviewRepository.findByItem(item);

        assertThat(findReview.size()).isEqualTo(1);

        User user2 = givenUser("test용 user2");
        Review review2 = Review.builder()
                .user(user2)
                .item(item)
                .score(1)
                .content("review save test2").build();
        reviewRepository.save(review2);
        List<Review> findReview2 = reviewRepository.findByItem(item);

        assertThat(findReview2.size()).isEqualTo(2);
    }


    public User givenUser(String name){
        User user = User.builder()
                .username(name)
                .password("1111")
                .roleType(RoleType.USER)
                .build();
        return userRepository.save(user);
    }

    public Item givenItem(String name){
        Item item = Item.builder()
                .name(name)
                .build();
        return itemRepository.save(item);
    }
}