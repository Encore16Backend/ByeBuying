package com.encore.byebuying.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public OrderHistory findById(Long id) {
		return orderHistoryRepo.findOrderHistoryById(id);
	}

	@Override
	public Page<OrderHistory> findByUsername(Pageable pageable, String username) {
		log.info("get OrderHistory by Username : {}",username);
		return orderHistoryRepo.findByUsername(pageable,username);
	}

	@Override
	public Page<OrderHistory> findByUsernameAndBetweenDate
			(Pageable pageable, String username, String start, String end) throws ParseException {
		log.info("Get OrderHistory Date start: {}, end: {}", start, end);
		Date dateStart = new Date(sdf.parse(start).getTime());
		Date dateEnd = new Date(sdf.parse(end).getTime());
		return orderHistoryRepo.findByDateBetweenAndUsername(pageable, dateStart, dateEnd, username);
	}

	@Override
	public void saveOrderHistory(List<OrderHistory> orderHistory) {
		log.info("Save OrderHistories: List");
		orderHistoryRepo.saveAll(orderHistory);
	}

	@Override
	public void saveOrderHistory(OrderHistory orderHistory) {
		log.info("Save OrderHistory");
		orderHistoryRepo.save(orderHistory);
	}


	@Override
	public void deleteOrderHistory(Long id) {
		log.info("delete OrderHistory id : {}",id);
		orderHistoryRepo.deleteById(id);
	}
}
