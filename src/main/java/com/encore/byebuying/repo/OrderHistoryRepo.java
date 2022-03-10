package com.encore.byebuying.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.encore.byebuying.domain.OrderHistory;

public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Long>{
	Page<OrderHistory> findByUsername(Pageable pageable,String username);

	void deleteAllByUsername(String username);
}
