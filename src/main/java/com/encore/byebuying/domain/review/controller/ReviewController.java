package com.encore.byebuying.domain.review.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.review.Review;
import com.encore.byebuying.domain.item.service.ItemService;
import com.encore.byebuying.domain.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	private final ItemService itemService;
	private final OrderService orderHistoryService;
	private final int PAGECOUNT = 5;

//	@GetMapping("/byItemid") // 상품 디테일에서 사용
//	public ResponseEntity<Page<Review>> getReviewByItemid(
//			@RequestParam(defaultValue="",value="itemid") Long itemid,
//			@RequestParam(defaultValue="date",value="sortname") String sortname,
//			@RequestParam(defaultValue="DESC",value="asc") String asc,
//			@RequestParam(required = false, defaultValue="1",value="page") int page){
//		Sort sort;
//		if(asc.equals("ASC") || asc.equals("asc")) {
//			sort = Sort.by(Sort.Direction.ASC, sortname);
//		}else {
//			sort = Sort.by(Sort.Direction.DESC, sortname);
//		}
//
//        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,sort);
//        Page<Review> review = reviewService.getByItemid(pageable,itemid);
//		return ResponseEntity.ok().body(review);
//	}
//
//	@GetMapping("/getReviews") // 리뷰 관리 페이지 사용(유저, 관리자)
//	public ResponseEntity<Page<Review>> getReviewByUsername(
//			@RequestParam(required = false, defaultValue="", value="username") String username,
//			@RequestParam(required = false, defaultValue="", value="itemname") String itemname,
//			@RequestParam(required = false, defaultValue = "", value = "start") String start,
//			@RequestParam(required = false, defaultValue = "", value = "end") String end,
//			@RequestParam(required = false, defaultValue="1",value="page") int page) throws ParseException {
//		Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
//				Sort.by(Sort.Direction.ASC, "date"));
//        Page<Review> reviews;
//
//		if (username.equals("") && itemname.equals("")) {
//			if (start.equals("") || end.equals(""))
//				reviews = reviewService.getReviews(pageable);
//			else
//				reviews = reviewService.getReviews(pageable, start, end);
//		} else if (username.equals("")){
//			if (start.equals("") || end.equals(""))
//				reviews = reviewService.getItemname(pageable, itemname);
//			else
//				reviews = reviewService.getItemname(pageable, start, end, itemname);
//		} else if (itemname.equals("")) {
//			if (start.equals("") || end.equals(""))
//				reviews = reviewService.getUsername(pageable, username);
//			else
//				reviews = reviewService.getUsername(pageable, start, end, username);
//		} else {
//			if (start.equals("") || end.equals(""))
//				reviews = reviewService.getUserNItem(pageable, username, itemname);
//			else
//				reviews = reviewService.getUserNItem(pageable, start, end, username, itemname);
//		}
//
//		return ResponseEntity.ok().body(reviews);
//	}
//
//	@PostMapping("/save")
//	public String saveReview(@RequestBody Map<String, Map<String, Object>> saveForm) {
//		Map<String, Object> reviewSave = saveForm.get("reviewSave");
//		ObjectMapper mapper = new ObjectMapper();
//
//		Review review = mapper.convertValue(reviewSave.get("review"), Review.class);
//		review.setDate(new Date());
//		reviewService.saveReview(review);
//
//		Long orderid = mapper.convertValue(reviewSave.get("orderid"), Long.class);
//		Order order = orderHistoryService.findById(orderid);
////		order.setChkreview(1);
//		orderHistoryService.saveOrderHistory(order);
//
//		Item item = itemService.getItemByItemid(review.getItemid());
//		/*
//		//이전에 만든 아이템 리뷰평균, 개수 저장 방법
//		item.setReviewmean(Double.parseDouble(reviewService.getAvgScoreByItemname(item.getItemname())));
//		item.setReviewcount(reviewService.countScoreByItemname(item.getItemname()));
//		*/
//		// 기존 개수 얻기
////		int itemCount = item.getReviewcount();
////		if(itemCount==0) {
////			item.setReviewmean(review.getScore());
////		}else {
////			// ( 리뷰평균x리뷰개수+새 리뷰점수 )/(리뷰개수+1)
////			item.setReviewmean((item.getReviewmean()*itemCount+review.getScore())/(itemCount+1));
////		}
////		item.setReviewcount(itemCount+1);
//
//		itemService.saveItem(item);
//		return "SUCCESS";
//	}
//
//	@DeleteMapping("/delete")
//	public ResponseEntity<?> deleteReview(
//			@RequestParam(defaultValue = "", value="reviewid[]") Long[] reviewid,
//			@RequestParam(defaultValue = "", value="itemid[]") Long[] itemid){
//
//		if (reviewid.length != itemid.length) {
//			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
//		}
//
//		System.out.println(Arrays.toString(reviewid));
//		System.out.println(Arrays.toString(itemid));
//
// 		int length = reviewid.length;
//		for (int i=0; i<length; i++){
//			Long ri = reviewid[i];
//			Long ii = itemid[i];
//
//			// 리뷰랑 아이템 조회
//			Review review = reviewService.getReview(ri);
//			Item item = itemService.getItemByItemid(ii);
//
//			/*
//			//이전 아이템 업데이트
//			item.setReviewmean(Double.parseDouble(reviewService.getAvgScoreByItemname(item.getItemname())));
//			item.setReviewcount(reviewService.countScoreByItemname(item.getItemname()));
//			*/
//
//			// 업데이트
////			int reviewCount = item.getReviewcount();
////			if(reviewCount==1) {
////				item.setReviewmean(0.0);
////			}else {
////				//(리뷰 평균x리뷰 개수 - 지울 리뷰 점수) / (리뷰 개수 -1)
////				item.setReviewmean(((item.getReviewmean()*reviewCount)-review.getScore())/(reviewCount-1));
////			}
////			item.setReviewcount(reviewCount-1);
//
//			reviewService.deleteReviewById(ri);
//			itemService.saveItem(item);
//		}
//		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//	}
//
//	@Transactional
//	@PutMapping("/update")
//	public ResponseEntity<?> updateReview(@RequestBody Review changedReview){
//		System.out.println(changedReview);
//		Review review = reviewService.getReview(changedReview.getId());
//		System.out.println(review);
//		// 리뷰 점수가 다르면 아이템에 평균 리뷰 업데이트
//		if(review.getScore()!=changedReview.getScore()) {
//			Item item = itemService.getItemByItemid(review.getItemid());
//			// (리뷰평균*리뷰개수-기존 리뷰점수+새리뷰점수)/리뷰개수
//
//			/*
//			//이전 아이템 업데이트
//			item.setReviewmean(Double.parseDouble(reviewService.getAvgScoreByItemname(item.getItemname())));
//			item.setReviewcount(reviewService.countScoreByItemname(item.getItemname()));
//			*/
////			item.setReviewmean((item.getReviewmean()*item.getReviewcount()-review.getScore()+changedReview.getScore())/item.getReviewcount());
//			itemService.saveItem(item);
//		}
//
//		// update
//		review.setDate(new Date());
//		review.setScore(changedReview.getScore());
//		review.setContent(changedReview.getContent());
//		reviewService.saveReview(review);
//		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//	}
//
//	// 테스트용 만들어봄
//	@GetMapping("/avg")
//	public String getAvgScoreByItemname(@RequestParam(defaultValue = "", value = "itemname") String itemname) {
//		return reviewService.getAvgScoreByItemname(itemname)+" "+reviewService.countScoreByItemname(itemname);
//	}
}