package com.encore.byebuying.domain.order.service;

import com.encore.byebuying.config.Exception.ResourceNotFoundException;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.order.dto.OrderItemInfoDTO;
import com.encore.byebuying.domain.order.dto.OrderRequestDTO;
import com.encore.byebuying.domain.order.dto.OrderResponseDTO;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.OrderItem;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.order.repository.OrderRepository;
import com.encore.byebuying.domain.user.repository.LocationRepository;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ItemRepository itemRepository;
	private final LocationRepository locationRepository;

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Transactional
	public OrderResponseDTO order(OrderRequestDTO orderRequestDto) {
		List<Long> itemIdList = orderRequestDto.getItems().stream().map(OrderItemInfoDTO::getItemId).collect(Collectors.toList());

		// 필요 엔티티 조회
		User findUser = userRepository.findById(orderRequestDto.getUserId())
				.orElseThrow(() -> new RuntimeException("user not found"));
		Location location = locationRepository.findById(orderRequestDto.getLocationId())
				.orElseThrow(() -> new RuntimeException("location not found"));



		List<Item> items = itemRepository.findByIds(itemIdList);
		Map<Long, Item> search = new HashMap<>();
		for (Item item : items) {
			search.put(item.getId(), item);
		}

		// OrderItem 생성
		List<OrderItemInfoDTO> infos = orderRequestDto.getItems();
		List<OrderItem> orderItems = infos.stream()
				.map(info -> OrderItem.createOrderItem(search.get(info.getItemId()), info.getCount(), info.getOrderPrice()))
				.collect(Collectors.toList());

		// 생성 메서드 (연관관계 메서드 사용됨)
		Order order = Order.createOrder(findUser, orderItems, new Address(location));
		orderRepository.save(order);

		return new OrderResponseDTO(order);
	}

	@Transactional
	public void cancelOrder(Long orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		Order order = optionalOrder.orElseThrow(() -> new NullPointerException());

		order.cancel();
	}

	@Override
	public OrderResponseDTO findById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Not found Order"));
		return new OrderResponseDTO(order);
	}

	@Override
	public Page<Order> findByUsername(Pageable pageable, String username) {
		log.info("get OrderHistory by Username : {}",username);
		return orderRepository.findByUsername(pageable, username);
	}

	@Override
	public Page<Order> findByUsernameAndBetweenDate
			(Pageable pageable, String username, String start, String end) throws ParseException {
		log.info("Get OrderHistory Date start: {}, end: {}", start, end);
		Date dateStart = new Date(sdf.parse(start).getTime());
		Date dateEnd = new Date(sdf.parse(end).getTime());
		return orderRepository.findByCreatedAtBetweenAndUser(pageable, dateStart, dateEnd, username);
	}

	@Override
	@Transactional
	public void deleteOrder(Long id) {
		log.info("delete OrderHistory id : {}",id);
		orderRepository.deleteById(id);
	}
}
