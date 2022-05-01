package com.encore.byebuying.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistory extends BaseTimeEntity {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_history_id")
	private Long id;

	// 유저 정보 또한 User id 를 가질거임
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String username;

	// 주문된 상품에 들어갈 거임
	private int bcount; // 개수

	private String location;

	private int chkreview; // 리뷰 체크

	// 하나의 주문내역은 여러 주문된 아이템들을 가짐
	@Builder.Default
	@OneToMany(mappedBy = "orderHistory")
	private List<OrderItem> items = new ArrayList<>();

	// 주문 내역에는 여러 아이템이 존재 --> 아이템 리스트로 바꿀거임
	private Long itemid;
	private String itemimg;
	private String itemname;
	private int itemprice;

	@Enumerated(value = EnumType.STRING)
	private OrderState orderState;

	// Date 클래스는 사용하지 않는것이 좋다.
	@Temporal(DATE)
	private Date date; // 구매 날짜
}

