package com.encore.byebuying.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.encore.byebuying.domain.Review;
import com.encore.byebuying.repo.ReviewRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepo reviewRepo;

	@Override
	public Page<Review> getReviews(Pageable pageable) {
		return reviewRepo.findAll(pageable);
	}
	@Override
	public Review getReview(Long id) {
		return reviewRepo.findReviewById(id);
	}
	@Override
	public Review saveReview(Review review) {
		log.info("Saving new review of {} by {} to the database", review.getItemname(),review.getUsername());
		if(review.getDate()==null)review.setDate(new Date());
		return reviewRepo.save(review);
	}
	@Override
	public void deleteReviewById(Long id) {
        log.info("Delete Review By Review ID");
		reviewRepo.deleteById(id);
	}
	@Override
	public Page<Review> getByItemid(Pageable pageable,Long itemid) {
		return reviewRepo.findByItemid(pageable,itemid);
	}
	@Override
	public Page<Review> getByUsername(Pageable pageable,String username) {
		return reviewRepo.findByUsername(pageable,username);
	}
	@Override
	public String getAvgScoreByItemname(String itemname) {
		String score = String.format("%.2f",reviewRepo.getAvgScoreByItemname(itemname));
		if (score.equals("nu")) {
			score = "0.0";
		}
		return score;
	}
	@Override
	public int countScoreByItemname(String itemname) {
		return reviewRepo.CountScoreByItemname(itemname);
	}
	

}
