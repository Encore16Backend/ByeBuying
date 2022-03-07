package com.encore.byebuying.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encore.byebuying.domain.OrderHistory;
import com.encore.byebuying.repo.OrderHistoryRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderHistoryServiceImpl implements OrderHistoryService {
	private final OrderHistoryRepo orderHistoryRepo;
	
	@Override
	public Page<OrderHistory> findByUsername(Pageable pageable, String username) {
		log.info("get OrderHistory by Username : {}",username);
		return orderHistoryRepo.findByUsername(pageable,username);
	}
	
	@Override
	public void saveOrderHistory(List<OrderHistory> orderHistory) {
		log.info("Save All OrderHistory");
		orderHistoryRepo.saveAll(orderHistory);
	}

	@Override
	public void deleteOrderHistory(Long id) {
		log.info("delete OrderHistory id : {}",id);
		orderHistoryRepo.deleteById(id);
	}
}
