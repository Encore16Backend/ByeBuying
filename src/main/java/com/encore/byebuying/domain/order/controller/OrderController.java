package com.encore.byebuying.domain.order.controller;

import com.encore.byebuying.config.auth.LoginUser;
import com.encore.byebuying.domain.common.paging.PagingRequest;
import com.encore.byebuying.domain.order.dto.OrderListVO;
import com.encore.byebuying.domain.order.dto.OrderRequestDTO;
import com.encore.byebuying.domain.order.dto.OrderResponseDTO;
import com.encore.byebuying.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	@GetMapping("")
	public ResponseEntity<?> getPageOrders(@AuthenticationPrincipal LoginUser loginUser, @ModelAttribute PagingRequest page) throws ParseException {
		String username = loginUser.getUsername();

		Pageable pageable = page.getPageRequest();
		Page<OrderListVO> orderListVOPage;
		if (page.isEmpty()) {
			orderListVOPage = OrderListVO.toPageOrderListVO(
					orderService.getPageOrders(pageable, username)
			);

		} else {
			orderListVOPage =
					OrderListVO.toPageOrderListVO(
							orderService.getPageOrdersAndBetweenDate(pageable, username, page.getStartDate(), page.getEndDate())
					);
		}
		return new ResponseEntity<>(orderListVOPage, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
		OrderResponseDTO orderResponseDTO;
		try {
			orderResponseDTO = orderService.findById(orderId);
		} catch (RuntimeException e) {
			return new ResponseEntity<>("FAIL", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
	}

	// List<OrderHistory> orderHistory: JSON parse error
	// => deserialize value of type `java.util.ArrayList<com.encore.byebuying.domain.OrderHistory>` from Object value (token `JsonToken.START_OBJECT`);
	@PostMapping("")
	public ResponseEntity<?> order(@RequestBody OrderRequestDTO orderRequestDTO) {
		OrderResponseDTO orderResult;
		try {
			orderResult = orderService.order(orderRequestDTO);
		} catch (Exception e) {
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(orderResult, HttpStatus.OK);
	}

	@DeleteMapping("/{order_id}")
	public ResponseEntity<?> deleteOrderHistory(@PathVariable("order_id") Long id) {
		orderService.deleteOrder(id);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}
