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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class ReviewResource {
	private final ReviewService reviewService;
	private final ItemService itemService;

	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> getReviews(){
		List<Review> review = reviewService.getReviews();
		return ResponseEntity.ok().body(review);
	}

	@GetMapping("/review/byItemname")
	public ResponseEntity<List<Review>> getReviewByItemnameOrderByDateDesc(@RequestBody Item item){
		String itemname = item.getItemname();
		List<Review> review = reviewService.findByItemnameOrderByDateDesc(itemname);
		return ResponseEntity.ok().body(review);
	}
	
	@GetMapping("/review/byUsername")
	public ResponseEntity<List<Review>> getReviewByUsernameOrderByDateDesc(@RequestBody User user){
		String username = user.getUsername();
		List<Review> review = reviewService.findByUsernameOrderByDateDesc(username);
		return ResponseEntity.ok().body(review);
	}
	
	@PostMapping("/review/save")
	public String saveReview(@RequestBody Review review){
		if(review.getDate()==null)review.setDate(new Date());
		reviewService.saveReview(review);
		return "SUCCESS";
	}
	
	@DeleteMapping("/review/delete")
	public ResponseEntity<?> deleteReview(@RequestBody Review review){
		reviewService.deleteReview(review.getId());
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
	
	@Transactional
	@PutMapping("/review/update")
	public ResponseEntity<?> updateReview(@RequestBody Review changedReview){
		Review review = reviewService.getReview(changedReview.getId());
		review.setDate(new Date());
		review.setScore(changedReview.getScore());
		review.setContent(changedReview.getContent());
		reviewService.saveReview(review);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}

class ReviewForm{
	
}
