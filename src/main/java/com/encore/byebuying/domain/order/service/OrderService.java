package com.encore.byebuying.domain.order.service;

import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.OrderItem;
import com.encore.byebuying.domain.order.dto.OrderItemInfoVO;
import com.encore.byebuying.domain.order.dto.OrderRequestVO;
import com.encore.byebuying.domain.order.dto.OrderResponseVO;
import com.encore.byebuying.domain.order.repository.OrderRepository;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.repository.location.LocationRepository;
import com.encore.byebuying.domain.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ItemRepository itemRepository;
	private final LocationRepository locationRepository;

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Transactional
	public OrderResponseVO order(OrderRequestVO orderRequestVO) {
		List<Long> itemIdList = orderRequestVO.getItems().stream().map(OrderItemInfoVO::getOrderItemId).collect(Collectors.toList());

		// 필요 엔티티 조회
		User findUser = userRepository.findById(orderRequestVO.getUserId())
				.orElseThrow(() -> new RuntimeException("user not found"));
		Location location = locationRepository.findById(orderRequestVO.getLocationId())
				.orElseThrow(() -> new RuntimeException("location not found"));


		List<Item> items = itemRepository.findByIds(itemIdList);
		Map<Long, Item> search = new HashMap<>();
		for (Item item : items) {
			search.put(item.getId(), item);
		}
		// OrderItem 생성
		List<OrderItemInfoVO> infos = orderRequestVO.getItems();
		List<OrderItem> orderItems = infos.stream()
				.map(info -> OrderItem.createOrderItem(search.get(info.getOrderItemId()), info.getCount(), info.getOrderPrice()))
				.collect(Collectors.toList());

		// 생성 메서드 (연관관계 메서드 사용됨)
		Order order = Order.createOrder(findUser, orderItems, new Address(location));
		orderRepository.save(order);

		return new OrderResponseVO(order);
	}

	@Transactional
	public void cancelOrder(Long orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		Order order = optionalOrder.orElseThrow(() -> new NullPointerException());

		order.cancel();
	}

	public OrderResponseVO findById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Not found Order"));
		return new OrderResponseVO(order);
	}

	public Page<Order> getPageOrders(Pageable pageable, String username) {
		log.info("get OrderHistory by Username : {}",username);
		return orderRepository.findByUsername(pageable, username);
	}

	public Page<Order> getPageOrdersAndBetweenDate
			(Pageable pageable, String username, LocalDateTime startDate, LocalDateTime endDate) throws ParseException {
		log.info("Get OrderHistory Date start: {}, end: {}", startDate, endDate);
		return orderRepository.findByCreatedAtBetweenAndUser(pageable, startDate, endDate, username);
	}

	@Transactional
	public void deleteOrder(Long id) {
		log.info("delete OrderHistory id : {}",id);
		orderRepository.deleteById(id);
	}
}
