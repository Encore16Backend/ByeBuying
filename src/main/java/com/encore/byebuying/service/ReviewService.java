package com.encore.byebuying.service;

import java.util.List;

import com.encore.byebuying.domain.Review;

public interface ReviewService {
	List<Review> getReviews(); // 전체 리뷰 조회 // 필요하려나
	Review getReview(Long id); // 리뷰 1개 조회
	Review saveReview(Review review); // 리뷰 저장
	List<Review> getByItemname(String itemname,String sortname,String asc); // itemname 기준 리뷰 조회, sort 키워드 기준으로 정렬
	List<Review> getByUsername(String username,String sortname,String asc); // username 기준 리뷰 조회, sort 키워드 기준으로 정렬
	
	void deleteReview(Long id);
	String getAvgScoreByItemname(String itemname);
}
