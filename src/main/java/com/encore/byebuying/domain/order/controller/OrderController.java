package com.encore.byebuying.domain.order.controller;

import java.text.ParseException;

import com.encore.byebuying.domain.order.dto.OrderDTO;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.basket.service.BasketService;
import com.encore.byebuying.domain.item.service.ItemService;
import com.encore.byebuying.domain.platfrom2server.service.WebClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encore.byebuying.domain.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderHistory")
public class OrderController {
	private final OrderService orderService;
	private final BasketService basketService;
	private final ItemService itemService;
	private final WebClientService webClientService;

	// List<OrderHistory> orderHistory: JSON parse error
	// => deserialize value of type `java.util.ArrayList<com.encore.byebuying.domain.OrderHistory>` from Object value (token `JsonToken.START_OBJECT`);
	@PostMapping("/add")
	public ResponseEntity<?> addOrderHistory(@RequestBody OrderDTO orderDTO) {
		try {
			orderService.order(orderDTO);
		} catch (Exception e) {
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("/getOrderHistories")
	public ResponseEntity<Page<Order>> getOrderHistories(
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

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteOrderHistory(
			@RequestParam(defaultValue = "", value = "basketid") Long id) {

		orderService.deleteOrder(id);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}
