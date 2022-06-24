package com.encore.byebuying.domain.review.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.encore.byebuying.domain.review.Review;
import com.encore.byebuying.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Review save(Review review) {
		return reviewRepository.save(review);
	}

	//	@Override
//	public Review getReview(Long id) {
//		return reviewRepository.findReviewById(id);
//	}
//
//	@Override
//	public Review saveReview(Review review) {
//		log.info("Saving new review of {} by {} to the database", review.getItemname(),review.getUsername());
//		if(review.getDate()==null)review.setDate(new Date());
//		return reviewRepository.save(review);
//	}
//
//	@Override
//	public void deleteReviewById(Long id) {
//        log.info("Delete Review By Review ID");
//		reviewRepository.deleteById(id);
//	}
//
//	@Override
//	public String getAvgScoreByItemname(String itemname) {
//		String score = String.format("%.2f", reviewRepository.getAvgScoreByItemname(itemname));
//		if (score.equals("nu")) {
//			score = "0.0";
//		}
//		return score;
//	}
//
//	@Override
//	public int countScoreByItemname(String itemname) {
//		return reviewRepository.CountScoreByItemname(itemname);
//	}
//
//	@Override
//	public Page<Review> getByItemid(Pageable pageable,Long itemid) {
//		return reviewRepository.findByItemid(pageable,itemid);
//	}
//
//	@Override
//	public Page<Review> getReviews(Pageable pageable) {
//		return reviewRepository.findAll(pageable);
//	}
//
//	@Override
//	public Page<Review> getReviews(Pageable pageable, String start, String end) throws ParseException {
//		log.info("Get Inquiry Date start: {}, end: {}", start, end);
//		Date dateStart = new Date(sdf.parse(start).getTime());
//		Date dateEnd = new Date(sdf.parse(end).getTime());
//		return reviewRepository.findByDateBetween(pageable, dateStart, dateEnd);
//	}
//
//	@Override
//	public Page<Review> getUsername(Pageable pageable, String username) {
//		return reviewRepository.findByUsername(pageable, username);
//	}
//
//	@Override
//	public Page<Review> getUsername(Pageable pageable, String start, String end, String username) throws ParseException {
//		log.info("Get Inquiry Date start: {}, end: {}", start, end);
//		Date dateStart = new Date(sdf.parse(start).getTime());
//		Date dateEnd = new Date(sdf.parse(end).getTime());
//		return reviewRepository.findByDateBetweenAndUsername(pageable, dateStart, dateEnd, username);
//	}
//
//	@Override
//	public Page<Review> getItemname(Pageable pageable, String itemname) {
//		return reviewRepository.findByItemnameContainingIgnoreCase(pageable, itemname);
//	}
//
//	@Override
//	public Page<Review> getItemname(Pageable pageable, String start, String end, String itemname) throws ParseException {
//		log.info("Get Inquiry Date start: {}, end: {}", start, end);
//		Date dateStart = new Date(sdf.parse(start).getTime());
//		Date dateEnd = new Date(sdf.parse(end).getTime());
//		return reviewRepository.findByDateBetweenAndItemnameContainingIgnoreCase(pageable, dateStart, dateEnd, itemname);
//	}
//
//	@Override
//	public Page<Review> getUserNItem(Pageable pageable, String username, String itemname) {
//		return reviewRepository.findByUsernameAndItemnameContainingIgnoreCase(pageable, username, itemname);
//	}
//
//	@Override
//	public Page<Review> getUserNItem(Pageable pageable, String start, String end, String username, String itemname) throws ParseException {
//		log.info("Get Inquiry Date start: {}, end: {}", start, end);
//		Date dateStart = new Date(sdf.parse(start).getTime());
//		Date dateEnd = new Date(sdf.parse(end).getTime());
//		return reviewRepository.findByDateBetweenAndUsernameAndItemnameContainingIgnoreCase(pageable, dateStart, dateEnd, username, itemname);
//	}
}
