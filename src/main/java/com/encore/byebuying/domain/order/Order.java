package com.encore.byebuying.domain.order;

import com.encore.byebuying.domain.code.OrderType;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.order.dto.OrderResponseVO;
import com.encore.byebuying.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id")
	private Long id;

	// 유저 정보 또한 User id 를 가질거임
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 하나의 주문내역은 여러 주문된 아이템들을 가짐
	@Builder.Default
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

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
		orderItems.add(orderItem);
	}
	public OrderResponseVO toOrderResponseDTO() {
		return new OrderResponseVO(this);
	}

	// 참고(내용 좋음): https://velog.io/@park2348190/JPA-Entity%EC%9D%98-equals%EC%99%80-hashCode
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Order)) {
			return false;
		}
		Order order = (Order) obj;
		return Objects.equals(this.id, order.getId()) &&
				this.user.equals(order.getUser());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user.getUsername(), orderItems, orderType, getCreatedAt());
	}
}

