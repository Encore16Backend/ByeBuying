package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.dto.OrderListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface OrderRepositoryCustom {
    // @Query("select o from Order o where o.user.username = :username")
    Page<OrderListVO> findByUsername(Pageable pageable, String username);

    Page<OrderListVO> findByCreatedAtBetweenAndUser(Pageable pageable, LocalDateTime start, LocalDateTime end, String username);
}
