package com.encore.byebuying.domain.order.controller;

import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.dto.OrderRequestDTO;
import com.encore.byebuying.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;

	@GetMapping("")
	public ResponseEntity<Page<Order>> getOrders(
			@RequestParam(defaultValue = "", value = "username") String username,
			@RequestParam(required = false, defaultValue = "", value = "start") String start,
			@RequestParam(required = false, defaultValue = "", value = "end") String end,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.ASC, "date"));
		Page<Order> orderHistories;
		if (start.equals("") || end.equals("")) {
			orderHistories =
					orderService.findByUsername(pageable, username);
		} else {
			orderHistories =
					orderService.findByUsernameAndBetweenDate(pageable, username, start, end);
		}
		return ResponseEntity.ok().body(orderHistories);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity getOrder(@PathVariable Long orderId) {
		orderService.findById(orderId);

		return ResponseEntity.ok().body(null);
	}

	// List<OrderHistory> orderHistory: JSON parse error
	// => deserialize value of type `java.util.ArrayList<com.encore.byebuying.domain.OrderHistory>` from Object value (token `JsonToken.START_OBJECT`);
	@PostMapping("")
	public ResponseEntity<?> addOrderHistory(@RequestBody OrderRequestDTO orderRequestDTO) {
		try {
			orderService.order(orderRequestDTO);
		} catch (Exception e) {
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	@DeleteMapping("/{order_id}")
	public ResponseEntity<?> deleteOrderHistory(@PathVariable("order_id") Long id) {

		orderService.deleteOrder(id);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}
