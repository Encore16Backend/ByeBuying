package com.encore.byebuying.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.encore.byebuying.domain.Review;

public interface ReviewService {
	Page<Review> getReviews(Pageable pageable); // 전체 리뷰 조회 // 필요하려나
	Review getReview(Long id); // 리뷰 1개 조회
	Review saveReview(Review review); // 리뷰 저장
	Page<Review> getByItemname(Pageable pageable,String itemname); // itemname 기준 리뷰 조회
	Page<Review> getByUsername(Pageable pageable,String username); // username 기준 리뷰 조회
	
	void deleteReview(Long id);
	String getAvgScoreByItemname(String itemname);
	int countScoreByItemname(String itemname);
}
