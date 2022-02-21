package com.encore.byebuying.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.encore.byebuying.domain.Review;

public interface ReviewRepo extends JpaRepository<Review, Long>{
	Review findReviewById(Long id);
	List<Review> findByItemname(String itemname,Sort sort);
	List<Review> findByUsername(String findByUsername,Sort sort);
}
