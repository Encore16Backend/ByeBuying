package com.encore.byebuying.api;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import com.encore.byebuying.api.dto.OrderRequest;
import com.encore.byebuying.domain.Order;
import com.encore.byebuying.service.BasketService;
import com.encore.byebuying.service.ItemService;
import com.encore.byebuying.service.WebClientService;
import com.encore.byebuying.service.dto.OrderItemInfoServiceDto;
import com.encore.byebuying.service.dto.OrderItemsServiceOrderDto;
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

import com.encore.byebuying.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderHistory")
public class OrderResource {
	private final OrderService orderService;
	private final BasketService basketService;
	private final ItemService itemService;
	private final WebClientService webClientService;

	// List<OrderHistory> orderHistory: JSON parse error
	// => deserialize value of type `java.util.ArrayList<com.encore.byebuying.domain.OrderHistory>` from Object value (token `JsonToken.START_OBJECT`);
	@PostMapping("/add")
	public ResponseEntity<?> addOrderHistory(@RequestBody OrderRequest orderRequest) {
		try {
			List<OrderItemInfoServiceDto> infoServiceDtos =
					orderRequest.getItems().stream()
						.map(item -> new OrderItemInfoServiceDto(item.getItemId(), item.getCount(), item.getOrderPrice()))
						.collect(Collectors.toList());

			OrderItemsServiceOrderDto serviceDto =
					new OrderItemsServiceOrderDto(orderRequest.getUserId(), infoServiceDtos, orderRequest.getAddress());
			orderService.order(serviceDto);

//			webClientService.checkPurchaseHistory(username, itemids);
			// 이거 물어보기
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

//	@GetMapping("/orderHistories")
//	public ResponseEntity<Page<OrderHistory>> orderHistories(OrderHistorySearch search) {
//		return ResponseEntity.ok();
//	}


//	@GetMapping("/byUsername")
//    public ResponseEntity<Page<OrderHistory>> getOrderHistoryByUsername(
//            @RequestParam(defaultValue="",value="username") String username,
//            @RequestParam(required = false, defaultValue="1",value="page") int page) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        Pageable pageable = PageRequest.of(page-1, 5, sort);
//        Page<OrderHistory> orderHistories = orderHistoryService.findByUsername(pageable, username);
//        return ResponseEntity.ok().body(orderHistories);
//    }
//
//	@GetMapping("/byDate")
//	public ResponseEntity<Page<OrderHistory>> getOrderHistoryByDate(
//			@RequestParam(defaultValue = "", value = "username") String username,
//			@RequestParam(defaultValue = "", value = "start") String start,
//			@RequestParam(defaultValue = "", value = "end") String end,
//			@RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
//		Pageable pageable = PageRequest.of(page-1, 5,
//				Sort.by(Sort.Direction.ASC, "date"));
//		Page<OrderHistory> orderHistories = orderHistoryService.findByUsernameAndBetweenDate(pageable, username, start, end);
//		return ResponseEntity.ok().body(orderHistories);
//	}

	@DeleteMapping("/delete")

	public ResponseEntity<?> deleteOrderHistory(
			@RequestParam(defaultValue = "", value = "basketid") Long id) {

		orderService.deleteOrderHistory(id);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}
