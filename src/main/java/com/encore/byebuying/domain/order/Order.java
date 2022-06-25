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
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderItem> items = new ArrayList<>();

	@Embedded
	private Address address;

	@Enumerated(value = EnumType.STRING)
	private OrderType orderType;

	/** 주문 생성 */
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

	/** 주문 취소 */
	public void cancel() {
		if (orderType == OrderType.CANCEL) {
			throw new RuntimeException("이미 취소된 주문을 취소할 수 없습니다.");
		} else if (orderType == OrderType.SHIPPING) {
			throw new RuntimeException("배송중 취소할 수 없습니다.");
		}

		orderType = OrderType.CANCEL;
	}

	public void userAddOrder(User user) {
		user.getOrders().add(this);
	}

	/** 연관관계 */
	public void addOrderItem(OrderItem orderItem) {
		orderItem.setOrder(this);
		items.add(orderItem);
	}
}

