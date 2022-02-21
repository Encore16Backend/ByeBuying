package com.encore.byebuying.api;

import java.util.Date;
import java.util.List;

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

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewResource {
	private final ReviewService reviewService;
	private final ItemService itemService;

	@GetMapping("/all")
	public ResponseEntity<List<Review>> getReviews(){
		List<Review> review = reviewService.getReviews();
		return ResponseEntity.ok().body(review);
	}

	@GetMapping("/byItemname")
	public ResponseEntity<List<Review>> getReviewByItemname(@RequestBody GetReviewForm getReviewForm){
		List<Review> review = reviewService.getByItemname(getReviewForm.getItemname(),getReviewForm.getSortname(),getReviewForm.getAsc());
		return ResponseEntity.ok().body(review);
	}
	
	@GetMapping("/byUsername")
	public ResponseEntity<List<Review>> getReviewByUsername(@RequestBody GetReviewForm getReviewForm){
		List<Review> review = reviewService.getByUsername(getReviewForm.getUsername(),getReviewForm.getSortname(),getReviewForm.getAsc());
		return ResponseEntity.ok().body(review);
	}
	
	@PostMapping("/save")
	public String saveReview(@RequestBody Review review){
		if(review.getDate()==null)review.setDate(new Date());
		reviewService.saveReview(review);
		return "SUCCESS";
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteReview(@RequestBody Review review){
		reviewService.deleteReview(review.getId());
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
}

@Data
class GetReviewForm{
	String username;
	String itemname;
	String asc;
	String sortname;
}
