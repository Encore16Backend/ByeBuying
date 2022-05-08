package com.encore.byebuying.repo;


import com.encore.byebuying.domain.Order;
import com.encore.byebuying.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface OrderRepo extends JpaRepository<Order, Long>{
	@Query("select o from Order o where o.id = :id")
	Order findOrderById(Long id);
	@Query("select o from Order o where o.user.username = :username")
	Page<Order> findByUsername(Pageable pageable,@Param("username") String username);
	Page<Order> findByCreatedAtBetweenAndUser(Pageable pageable, Date start, Date end, String username);

	@Query("delete from Order o  where o.user.username = :username")
	void deleteAllByUsername(@Param("username") String username);
}
