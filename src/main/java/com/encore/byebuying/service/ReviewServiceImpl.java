package com.encore.byebuying.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.encore.byebuying.domain.Review;
import com.encore.byebuying.repo.ReviewRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepo reviewRepo;

	@Override
	public List<Review> getReviews() {
        log.info("Get All Review");
		return reviewRepo.findAll();
	}
	@Override
	public Review getReview(Long id) {
		return reviewRepo.getById(id);
	}
	@Override
	public Review saveReview(Review review) {
		log.info("Saving new review of {} by {} to the database", review.getItemname(),review.getUsername());
		if(review.getDate()==null)review.setDate(new Date());
		return reviewRepo.save(review);
	}
	@Override
	public List<Review> findByItemnameOrderByDateDesc(String itemname) {
        log.info("Get Review By Itemname");
		return reviewRepo.findByItemnameOrderByDateDesc(itemname);
	}
	@Override
	public List<Review> findByUsernameOrderByDateDesc(String username) {
        log.info("Get Review By Username");
		return reviewRepo.findByUsernameOrderByDateDesc(username);
	}
	@Override
	public void deleteReview(Long id) {
        log.info("Delete Review By Reviewid");
		reviewRepo.deleteById(id);
	}

}
