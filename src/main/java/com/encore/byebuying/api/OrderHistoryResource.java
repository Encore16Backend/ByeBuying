package com.encore.byebuying.api;

import java.util.List;
import java.util.Map;

import com.encore.byebuying.service.BasketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encore.byebuying.domain.OrderHistory;
import com.encore.byebuying.service.OrderHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderHistory")
public class OrderHistoryResource {
	private final OrderHistoryService orderHistoryService;
	private final BasketService basketService;

	// List<OrderHistory> orderHistory: JSON parse error
	// => deserialize value of type `java.util.ArrayList<com.encore.byebuying.domain.OrderHistory>` from Object value (token `JsonToken.START_OBJECT`);
	@PostMapping("/add")
	public ResponseEntity<?> addOrderHistory(@RequestBody Map<String, List<OrderHistory>> orderHistory) {
		try {
			List<OrderHistory> orderHistories = orderHistory.get("OrderHistory");
			orderHistoryService.saveOrderHistory(orderHistories);
			for (OrderHistory o: orderHistories) {
				basketService.deleteBasketByItemidAndUsername(o.getItemid(), o.getUsername());
			}
		} catch (Exception e){
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("/byUsername")
    public ResponseEntity<Page<OrderHistory>> getOrderHistoryByUsername(
            @RequestParam(defaultValue="",value="username") String username,
            @RequestParam(required = false, defaultValue="1",value="page") int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(page-1, 5, sort);
        Page<OrderHistory> orderHistories = orderHistoryService.findByUsername(pageable, username);
        return ResponseEntity.ok().body(orderHistories);
    }
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteOrderHistory(
			@RequestParam(defaultValue = "", value="basketid") Long id) {
		orderHistoryService.deleteOrderHistory(id);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}
