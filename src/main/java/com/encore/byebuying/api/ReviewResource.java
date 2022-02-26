package com.encore.byebuying.api;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.domain.Review;
import com.encore.byebuying.domain.User;
import com.encore.byebuying.service.ItemService;
import com.encore.byebuying.service.ReviewService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewResource {
	private final ReviewService reviewService;
	private final ItemService itemService;
	private final int PAGECOUNT = 5;
	@GetMapping("/all")
	public ResponseEntity<Page<Review>> getReviews(
			@RequestParam(defaultValue="date",value="sortname") String sortname,
			@RequestParam(defaultValue="DESC",value="asc") String asc,
			@RequestParam(required = false, defaultValue="1",value="page") int page){
		Sort sort;
		if(asc.equals("ASC") || asc.equals("asc")) {
			sort = Sort.by(Sort.Direction.ASC, sortname);
		}else {
			sort = Sort.by(Sort.Direction.DESC, sortname);
		}
		Pageable pageable = PageRequest.of(page-1, PAGECOUNT,sort);
        
		Page<Review> review = reviewService.getReviews(pageable);
		return ResponseEntity.ok().body(review);
	}

	@GetMapping("/byItemname")
	public ResponseEntity<Page<Review>> getReviewByItemname(
			@RequestParam(defaultValue="",value="itemname") String itemname,
			@RequestParam(defaultValue="date",value="sortname") String sortname,
			@RequestParam(defaultValue="DESC",value="asc") String asc,
			@RequestParam(required = false, defaultValue="1",value="page") int page){
		Sort sort;
		if(asc.equals("ASC") || asc.equals("asc")) {
			sort = Sort.by(Sort.Direction.ASC, sortname);
		}else {
			sort = Sort.by(Sort.Direction.DESC, sortname);
		}
		
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,sort);
        Page<Review> review = reviewService.getByItemname(pageable,itemname);
		return ResponseEntity.ok().body(review);
	}
	
	@GetMapping("/byUsername")
	public ResponseEntity<Page<Review>> getReviewByUsername(
			@RequestParam(defaultValue="",value="username") String username,
			@RequestParam(defaultValue="date",value="sortname") String sortname,
			@RequestParam(defaultValue="DESC",value="asc") String asc,
			@RequestParam(required = false, defaultValue="1",value="page") int page){
		Sort sort;
		if(asc.equals("ASC") || asc.equals("asc")) {
			sort = Sort.by(Sort.Direction.ASC, sortname);
		}else {
			sort = Sort.by(Sort.Direction.DESC, sortname);
		}
		
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,sort);
        Page<Review> review = reviewService.getByUsername(pageable,username);
		return ResponseEntity.ok().body(review);
	}
	
	@Transactional
	@PostMapping("/save")
	public String saveReview(@RequestBody Review review){
		if(review.getDate()==null)review.setDate(new Date());
		reviewService.saveReview(review);
		Item item = itemService.getItemByItemname(review.getItemname());
		item.setReviewmean(Double.parseDouble(reviewService.getAvgScoreByItemname(item.getItemname())));
		item.setReviewcount(reviewService.countScoreByItemname(item.getItemname()));
		itemService.saveItem(item);
		return "SUCCESS";
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteReview(
			@RequestParam(defaultValue = "", value="id") Long id,
			@RequestParam(defaultValue = "", value="itemname") String itemname
			){
		reviewService.deleteReviewById(id);
		Item item = itemService.getItemByItemname(itemname);
		item.setReviewmean(Double.parseDouble(reviewService.getAvgScoreByItemname(item.getItemname())));
		item.setReviewcount(reviewService.countScoreByItemname(item.getItemname()));
		itemService.saveItem(item);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
	
	@Transactional
	@PutMapping("/update")
	public ResponseEntity<?> updateReview(@RequestBody Review changedReview){
		Review review = reviewService.getReview(changedReview.getId());
		review.setDate(new Date());
		review.setScore(changedReview.getScore());
		review.setContent(changedReview.getContent());
		reviewService.saveReview(review);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
	
	// 테스트용 만들어봄
	@GetMapping("/avg")
	public String getAvgScoreByItemname(@RequestParam(defaultValue = "", value = "itemname") String itemname) {
		return reviewService.getAvgScoreByItemname(itemname)+" "+reviewService.countScoreByItemname(itemname);
	}
}