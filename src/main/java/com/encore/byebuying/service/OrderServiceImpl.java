package com.encore.byebuying.service;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.domain.Order;
import com.encore.byebuying.domain.OrderItem;
import com.encore.byebuying.domain.User;
import com.encore.byebuying.repo.ItemRepo;
import com.encore.byebuying.repo.OrderRepo;
import com.encore.byebuying.repo.UserRepo;
import com.encore.byebuying.service.dto.OrderItemInfoServiceDto;
import com.encore.byebuying.service.dto.OrderItemsServiceOrderDto;
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
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
	private final OrderRepo orderRepo;
	private final UserRepo userRepo;
	private final ItemRepo itemRepo;


	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Long order(OrderItemsServiceOrderDto orderDto) {
		List<Long> itemIdList = orderDto.getItems().stream().map(OrderItemInfoServiceDto::getItemId).collect(Collectors.toList());

		// 필요 엔티티 조회
		User findUser = userRepo.findById(orderDto.getUserId()).get();
		List<Item> items = itemRepo.findByIds(itemIdList);
		Map<Long, Item> search = new HashMap<>();
		for (Item item : items) {
			search.put(item.getId(), item);
		}

		// OrderItem 생성
		List<OrderItemInfoServiceDto> infos = orderDto.getItems();
		List<OrderItem> orderItems = infos.stream()
				.map(info -> OrderItem.createOrderItem(search.get(info.getItemId()), info.getCount(), info.getOrderPrice()))
				.collect(Collectors.toList());

		// 생성 메서드 (연관관계 메서드 사용됨)
		Order order = Order.createOrder(findUser, orderItems, findUser.getAddress());
		orderRepo.save(order);

		return order.getId();
	}

	@Override
	public Order findById(Long id) {
		return orderRepo.findOrderById(id);
	}

	@Override
	public Page<Order> findByUsername(Pageable pageable, String username) {
		log.info("get OrderHistory by Username : {}",username);
		return orderRepo.findByUsername(pageable,username);
	}

	@Override
	public Page<Order> findByUsernameAndBetweenDate
			(Pageable pageable, String username, String start, String end) throws ParseException {
		log.info("Get OrderHistory Date start: {}, end: {}", start, end);
		Date dateStart = new Date(sdf.parse(start).getTime());
		Date dateEnd = new Date(sdf.parse(end).getTime());
		return orderRepo.findByCreatedAtBetweenAndUser(pageable, dateStart, dateEnd, username);
	}

	@Override
	public void saveOrderHistory(List<Order> order) {
		log.info("Save OrderHistories: List");
		orderRepo.saveAll(order);
	}

	@Override
	public void saveOrderHistory(Order order) {
		log.info("Save OrderHistory");
		orderRepo.save(order);
	}


	@Override
	public void deleteOrderHistory(Long id) {
		log.info("delete OrderHistory id : {}",id);
		orderRepo.deleteById(id);
	}
}
