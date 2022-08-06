package com.encore.byebuying.domain.order.controller;

import com.encore.byebuying.config.auth.PrincipalUserAdapter;
import com.encore.byebuying.domain.order.dto.OrderRequestDTO;
import com.encore.byebuying.domain.order.dto.OrderResponseDTO;
import com.encore.byebuying.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;

	@GetMapping("test")
	public String securityTest(@AuthenticationPrincipal PrincipalUserAdapter principalUserAdapter) {
		log.info("test :: principalUserAdapter :: {}", principalUserAdapter.getUser());
		return principalUserAdapter.getUser().toString();
	}

	@GetMapping("")
	public ResponseEntity<?> getPageOrders(
			@RequestParam(defaultValue = "", value = "username") String username,
			@RequestParam(required = false, defaultValue = "", value = "start") String start,
			@RequestParam(required = false, defaultValue = "", value = "end") String end,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.ASC, "date"));
		Page<OrderResponseDTO> orderResponseDTOPage;
		if (start.equals("") || end.equals("")) {
			orderResponseDTOPage = OrderResponseDTO.toPageOrderResponseDTO(orderService.findByUsername(pageable, username));
		} else {
			orderResponseDTOPage =
					OrderResponseDTO.toPageOrderResponseDTO(orderService.findByUsernameAndBetweenDate(pageable, username, start, end));
		}
		return new ResponseEntity<>(orderResponseDTOPage, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
		OrderResponseDTO orderResponseDTO = null;
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
		OrderResponseDTO orderResult = null;
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
