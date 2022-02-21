package com.encore.byebuying.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
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
		return reviewRepo.findAll(Sort.by(Sort.Direction.DESC, "date"));
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
	public void deleteReview(Long id) {
        log.info("Delete Review By Review ID");
		reviewRepo.deleteById(id);
	}
	@Override
	public List<Review> getByItemname(String itemname, String sortname,String asc) {
		if(asc.equals("ASC") || asc.equals("asc")) {
			Sort sort = Sort.by(Sort.Direction.ASC, sortname);
			return reviewRepo.findByItemname(itemname,sort);
		}
		Sort sort = Sort.by(Sort.Direction.DESC, sortname);
		return reviewRepo.findByItemname(itemname,sort);
	}
	@Override
	public List<Review> getByUsername(String username, String sortname,String asc) {
		if(asc.equals("ASC") || asc.equals("asc")) {
			Sort sort = Sort.by(Sort.Direction.ASC, sortname);
			return reviewRepo.findByUsername(username,sort);
		}
		Sort sort = Sort.by(Sort.Direction.DESC, sortname);
		return reviewRepo.findByUsername(username,sort);
	}
	@Override
	public String getAvgScoreByItemname(String itemname) {
		return String.format("%.2f",reviewRepo.getAvgScoreByItemname(itemname));
	}

}
