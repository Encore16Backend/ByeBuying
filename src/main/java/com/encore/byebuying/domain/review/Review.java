package com.encore.byebuying.domain.review;

import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.user.User;
import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

	private String username; // 유저 네임


	private Long itemid; // 아이템 번호
	private String itemname; // 아이템 이름
	private String itemimage; // 아이템 이미지

	private double score; // 리뷰 점수
	private String content; // 리뷰 내용
	@Temporal(DATE)
	private Date date; // 리뷰 작성 날짜
}
