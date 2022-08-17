package com.encore.byebuying.domain.order.service;

import com.encore.byebuying.domain.order.dto.OrderRequestDTO;
import java.text.ParseException;

import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
	OrderResponseDTO order(OrderRequestDTO orderRequestDto);
	OrderResponseDTO findById(Long id);
	Page<Order> findByUsername(Pageable pageable, String username);
	Page<Order> findByUsernameAndBetweenDate(Pageable pageable, String username, String start, String end) throws ParseException;
	void deleteOrder(Long id);
}
