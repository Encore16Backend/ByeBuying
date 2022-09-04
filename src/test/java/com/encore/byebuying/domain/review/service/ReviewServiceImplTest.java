package com.encore.byebuying.domain.review.service;

import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.review.Review;
import com.encore.byebuying.domain.review.repository.ReviewRepository;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UpdateUserDTO;
import com.encore.byebuying.domain.user.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.encore.byebuying.domain.code.ProviderType.LOCAL;
import static org.assertj.core.api.Assertions.assertThat;

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

        //given
        User user = givenUser("test용 user1");
        Item item = givenItem("test용 item");

        //when
        Review review = Review.builder().user(user).item(item).score(5).content("review save test").build();
        Review saveReview = reviewRepository.save(review);

        //then
//        Review findReview = reviewRepository.findById(saveReview.getId()).get();
//        assertThat(saveReview).isEqualTo(findReview);
    }

    @Test
    void findByItemId() {
        //given
        User user1 = givenUser("test용 user1");
        Item item1 = givenItem("test용 item1");
        Item item2 = givenItem("test용 item2");

        //when
        Review review1 = Review.builder().user(user1).item(item1).score(5).content("review save test").build();
        Review review2 = Review.builder().user(user1).item(item2).score(5).content("review save test").build();
        Review review3 = Review.builder().user(user1).item(item2).score(5).content("review save test").build();
        Review saveReview1 = reviewRepository.save(review1);
        Review saveReview2 = reviewRepository.save(review2);
        Review saveReview3 = reviewRepository.save(review3);

        //then
        List<Review> findReviews1 = reviewRepository.findByItemId(item1.getId());
        List<Review> findReviews2 = reviewRepository.findByItemId(item2.getId());
        assertThat(findReviews1.size()).isEqualTo(1);
        assertThat(findReviews2.size()).isEqualTo(2);
    }
    @Test
    void findByUserId() {
        //given
        User user1 = givenUser("test용 user1");
        User user2 = givenUser("test용 user2");
        Item item1 = givenItem("test용 item1");
        Item item2 = givenItem("test용 item2");

        //when
        Review review1 = Review.builder().user(user1).item(item1).score(5).content("review save test").build();
        Review review2 = Review.builder().user(user2).item(item1).score(5).content("review save test").build();
        Review review3 = Review.builder().user(user2).item(item2).score(5).content("review save test").build();
        Review saveReview1 = reviewRepository.save(review1);
        Review saveReview2 = reviewRepository.save(review2);
        Review saveReview3 = reviewRepository.save(review3);

        //then
        List<Review> findReviews1 = reviewRepository.findByUserId(user1.getId());
        List<Review> findReviews2 = reviewRepository.findByUserId(user2.getId());
        assertThat(findReviews1.size()).isEqualTo(1);
        assertThat(findReviews2.size()).isEqualTo(2);
    }


    public User givenUser(String name){
        UpdateUserDTO dto = new UpdateUserDTO(null,"test", "1111","test@naver.com");
        User user = userRepository.save(
            User.updateUser()
                .dto(dto)
                .provider(LOCAL)
                .build());
        return userRepository.save(user);
    }

    public Item givenItem(String name){
        Item item = Item.builder()
                .name(name)
                .build();
        return itemRepository.save(item);
    }

}