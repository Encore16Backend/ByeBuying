package com.encore.byebuying.domain.review;

import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
import java.util.Date;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "review_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	private double score; // 리뷰 점수
	private String content; // 리뷰 내용

	// inquery는 public static인데 이유가?
//	public Review createReview(Long id, User user, Item item, double score, String content) {
//		Review review = Review.builder()
//				.id(id)
//				.user(user)
//				.item(item)
//				.score(score)
//				.content(content).build();
//
//		user.getReviews().add(review);
//		item.getReviews().add(review);
//
//		return review;
//	}
}
