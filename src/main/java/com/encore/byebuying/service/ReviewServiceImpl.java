package com.encore.byebuying.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.encore.byebuying.domain.Inquiry;
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
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

	@Override
	public Page<Review> getByItemid(Pageable pageable,Long itemid) {
		return reviewRepo.findByItemid(pageable,itemid);
	}

	@Override
	public Page<Review> getReviews(Pageable pageable) {
		return reviewRepo.findAll(pageable);
	}

	@Override
	public Page<Review> getReviews(Pageable pageable, String start, String end) throws ParseException {
		log.info("Get Inquiry Date start: {}, end: {}", start, end);
		Date dateStart = new Date(sdf.parse(start).getTime());
		Date dateEnd = new Date(sdf.parse(end).getTime());
		return reviewRepo.findByDateBetween(pageable, dateStart, dateEnd);
	}

	@Override
	public Page<Review> getUsername(Pageable pageable, String username) {
		return reviewRepo.findByUsername(pageable, username);
	}

	@Override
	public Page<Review> getUsername(Pageable pageable, String start, String end, String username) throws ParseException {
		log.info("Get Inquiry Date start: {}, end: {}", start, end);
		Date dateStart = new Date(sdf.parse(start).getTime());
		Date dateEnd = new Date(sdf.parse(end).getTime());
		return reviewRepo.findByDateBetweenAndUsername(pageable, dateStart, dateEnd, username);
	}

	@Override
	public Page<Review> getItemname(Pageable pageable, String itemname) {
		return reviewRepo.findByItemnameContainingIgnoreCase(pageable, itemname);
	}

	@Override
	public Page<Review> getItemname(Pageable pageable, String start, String end, String itemname) throws ParseException {
		log.info("Get Inquiry Date start: {}, end: {}", start, end);
		Date dateStart = new Date(sdf.parse(start).getTime());
		Date dateEnd = new Date(sdf.parse(end).getTime());
		return reviewRepo.findByDateBetweenAndItemnameContainingIgnoreCase(pageable, dateStart, dateEnd, itemname);
	}

	@Override
	public Page<Review> getUserNItem(Pageable pageable, String username, String itemname) {
		return reviewRepo.findByUsernameAndItemnameContainingIgnoreCase(pageable, username, itemname);
	}

	@Override
	public Page<Review> getUserNItem(Pageable pageable, String start, String end, String username, String itemname) throws ParseException {
		log.info("Get Inquiry Date start: {}, end: {}", start, end);
		Date dateStart = new Date(sdf.parse(start).getTime());
		Date dateEnd = new Date(sdf.parse(end).getTime());
		return reviewRepo.findByDateBetweenAndUsernameAndItemnameContainingIgnoreCase(pageable, dateStart, dateEnd, username, itemname);
	}
}
