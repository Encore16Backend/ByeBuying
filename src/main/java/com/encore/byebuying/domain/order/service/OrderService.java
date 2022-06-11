package com.encore.byebuying.domain.order.service;

import com.encore.byebuying.domain.order.dto.OrderDTO;
import java.text.ParseException;
import java.util.List;

import com.encore.byebuying.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
	Long order(OrderDTO orderDto);
	Order findById(Long id);
	Page<Order> findByUsername(Pageable pageable, String username);
	Page<Order> findByUsernameAndBetweenDate(Pageable pageable, String username, String start, String end) throws ParseException;
	void saveOrderHistory(List<Order> order);
	void saveOrderHistory(Order order);
	@Transactional
	void deleteOrderHistory(Long id);
}
