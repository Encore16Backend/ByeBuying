package com.encore.byebuying.api;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.service.BasketService;
import com.encore.byebuying.service.ItemService;
import com.encore.byebuying.service.WebClientService;
import org.aspectj.weaver.ast.Or;
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

import com.encore.byebuying.domain.OrderHistory;
import com.encore.byebuying.service.OrderHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderHistory")
public class OrderHistoryResource {
	private final OrderHistoryService orderHistoryService;
	private final BasketService basketService;
	private final ItemService itemService;
	private final WebClientService webClientService;

	// List<OrderHistory> orderHistory: JSON parse error
	// => deserialize value of type `java.util.ArrayList<com.encore.byebuying.domain.OrderHistory>` from Object value (token `JsonToken.START_OBJECT`);
	@PostMapping("/add")
	public ResponseEntity<?> addOrderHistory(@RequestBody Map<String, List<OrderHistory>> orderHistory) {
		try {
			List<OrderHistory> orderHistories = orderHistory.get("OrderHistory");

			Long[] itemids = new Long[orderHistories.size()];
			int idx = 0;
			String username = null;

			for (OrderHistory o: orderHistories) {
				o.setDate(new Date());

				Long itemid = o.getItemid();

				itemids[idx] = itemid;
				username = o.getUsername();
				idx++;

				basketService.deleteBasketByItemidAndUsername(itemid, o.getUsername());

				Item item = itemService.getItemByItemid(itemid);
				item.setPurchasecnt(item.getPurchasecnt()+o.getBcount());

				itemService.saveItem(item);
			}
			orderHistoryService.saveOrderHistory(orderHistories);
			webClientService.checkPurchaseHistory(username, itemids);
		} catch (Exception e){
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("/getOrderHistories")
	public ResponseEntity<Page<OrderHistory>> getOrderHistories(
			@RequestParam(defaultValue="", value="username") String username,
			@RequestParam(required = false, defaultValue = "", value = "start") String start,
			@RequestParam(required = false, defaultValue = "", value = "end") String end,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
		Pageable pageable = PageRequest.of(page-1, 5, Sort.by(Sort.Direction.ASC, "date"));
		Page<OrderHistory> orderHistories;
		if (start.equals("") && end.equals("")){
			orderHistories =
					orderHistoryService.findByUsername(pageable, username);
		} else {
			orderHistories =
					orderHistoryService.findByUsernameAndBetweenDate(pageable, username, start, end);
		}
		return ResponseEntity.ok().body(orderHistories);
	}
	
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
			@RequestParam(defaultValue = "", value="basketid") Long id) {
		orderHistoryService.deleteOrderHistory(id);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}
