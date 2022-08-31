package com.encore.byebuying.domain.order.repository;


import com.encore.byebuying.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>{
	@EntityGraph(attributePaths = {"items"})
	Optional<Order> findById(Long id);

	@Override
	@EntityGraph(attributePaths = {"items"})
	Order getById(Long aLong);

	@Query("select o from Order o where o.user.username = :username")
	Page<Order> findByUsername(Pageable pageable,@Param("username") String username);
	Page<Order> findByCreatedAtBetweenAndUser(Pageable pageable, LocalDateTime start, LocalDateTime end, String username);

	@Query("delete from Order o  where o.user.username = :username")
	void deleteAllByUsername(@Param("username") String username);
}
