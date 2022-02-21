package com.encore.byebuying.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encore.byebuying.domain.Review;

public interface ReviewRepo extends JpaRepository<Review, Long>{
	List<Review> findByItemnameOrderByDateDesc(String itemname);
	List<Review> findByUsernameOrderByDateDesc(String username);
}
