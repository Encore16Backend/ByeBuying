package com.encore.byebuying.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.encore.byebuying.domain.Review;

public interface ReviewRepo extends JpaRepository<Review, Long>{
	Review findReviewById(Long id);
	Page<Review> findByItemid(Pageable pageable,Long itemid);
	Page<Review> findByUsername(Pageable pageable,String findByUsername);

    @Query(value="select avg(score) from review where itemname like :keyword1", nativeQuery=true )
	Double getAvgScoreByItemname(@Param("keyword1") String itemname);
    @Query(value="select count(score) from review where itemname like :keyword1", nativeQuery=true )
   	int CountScoreByItemname(@Param("keyword1") String itemname);
}
