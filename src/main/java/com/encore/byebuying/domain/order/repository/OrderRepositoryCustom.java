package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.order.vo.OrderListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderRepositoryCustom {
    // @Query("select o from Order o where o.user.username = :username")
//    Page<OrderListVO> findByUsername(Pageable pageable, Long userId);

    Page<OrderListVO> findByCreatedAtBetweenAndUser(Pageable pageable, LocalDateTime start, LocalDateTime end, Long userId);
}
