package com.encore.byebuying.service;

import java.util.List;

import com.encore.byebuying.domain.Review;

public interface ReviewService {
	List<Review> getReviews(); // 전체 리뷰 조회 // 필요하려나
	Review getReview(Long id);
	Review saveReview(Review review); // 리뷰 저장
	List<Review> findByItemnameOrderByDateDesc(String itemname); // itemname 기준 리뷰 조회, 날짜 높은게 위로(최신?)
	List<Review> findByUsernameOrderByDateDesc(String username); // username 기준 리뷰 조회, 날짜 높은게 위로(최신?)
	
	void deleteReview(Long id);
	//double getAvgScoreByItemidGroupItemid(Long itemid);
}
