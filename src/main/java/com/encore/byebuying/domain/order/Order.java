package com.encore.byebuying.domain.order;

import com.encore.byebuying.domain.code.OrderType;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseTimeEntity {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id")
	private Long id;

	// 유저 정보 또한 User id 를 가질거임
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 하나의 주문내역은 여러 주문된 아이템들을 가짐
	@Builder.Default
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItem> items = new ArrayList<>();

	@Embedded
	private Address address;

	@Enumerated(value = EnumType.STRING)
	private OrderType orderType;

	public void userAddOrder(User user) {
		user.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setOrder(this);
		items.add(orderItem);
	}

	public static Order createOrder(User user, List<OrderItem> items, Address address) {
		Order order = Order.builder()
				.user(user)
				.address(address)
				.orderType(OrderType.RECEIPT)
				.build();

		for (OrderItem item : items) {
			order.addOrderItem(item);
		}

		order.userAddOrder(user);

		return order;
	}


//	private String location;

//	private String username;

	// 주문된 상품에 들어갈 거임
//	private int bcount; // 개수


	// 주문내역에 리뷰를 저장안함 -> 아이템을 통해서 리뷰를 찾아갈 것임
//	private int chkreview; // 리뷰 체크


	// 주문 내역에는 여러 아이템이 존재 --> 아이템 리스트로 바꿀거임
//	private Long itemid;
//	private String itemimg;
//	private String itemname;
//	private int itemprice;

	// Date 와 Calendar 클래스는 사용하지 않는것이 좋다. -> 개선한 것이 LocalDateTime
//	@Temporal(DATE)
//	private Date date; // 구매 날짜
}

