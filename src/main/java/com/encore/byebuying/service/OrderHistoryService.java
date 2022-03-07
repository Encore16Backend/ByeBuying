package com.encore.byebuying.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.encore.byebuying.domain.OrderHistory;

public interface OrderHistoryService {
	Page<OrderHistory> findByUsername(Pageable pageable,String username);
	void saveOrderHistory(List<OrderHistory> orderHistory);
	@Transactional
	void deleteOrderHistory(Long id);
}
