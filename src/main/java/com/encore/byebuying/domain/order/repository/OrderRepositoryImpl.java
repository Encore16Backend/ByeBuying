package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.dto.OrderItemListVO;
import com.encore.byebuying.domain.order.dto.OrderListVO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.encore.byebuying.domain.item.QItem.item;
import static com.encore.byebuying.domain.order.QOrder.order;
import static com.encore.byebuying.domain.order.QOrderItem.orderItem;
import static com.encore.byebuying.domain.user.QUser.user;

@Repository
@Transactional(readOnly = true)
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

     private final JPAQueryFactory query;

    public OrderRepositoryImpl(EntityManager em) {
        super(Order.class);
        this.query = new JPAQueryFactory(em);
    }

    //	@Query("select o from Order o where o.user.username = :username")
    @Override
    public Page<OrderListVO> findByUsername(Pageable pageable, String username) {
        JPAQuery<OrderListVO> jpaQuery = query
                .select(Projections.constructor(OrderListVO.class, order))
                .from(order)
                .join(order.user, user)
                .where(
                        usernameEq(username)
                );

        return getOrderListVOS(pageable, jpaQuery);
    }

    // TODO: 2022-09-06 할거임
    @Override
    public Page<OrderListVO> findByCreatedAtBetweenAndUser(Pageable pageable, LocalDateTime start, LocalDateTime end, String username) {
        JPAQuery<OrderListVO> jpaQuery = query
                .select(Projections.constructor(OrderListVO.class, order))
                .from(order)
                .join(order.user, user)
                .where(
                        usernameEq(username),
                        betweenDate(start, end)
                );

        return getOrderListVOS(pageable, jpaQuery);
    }

    private Page<OrderListVO> getOrderListVOS(Pageable pageable, JPAQuery<OrderListVO> jpaQuery) {
        List<OrderListVO> result = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();

        Map<Long, List<OrderItemListVO>> orderItemMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o -> o.setItems(orderItemMap.get(o.getOrderId())));

        return OrderListVO.toPageOrderListVO(result, pageable);
    }

    private Map<Long, List<OrderItemListVO>> findOrderItemMap(List<Long> ids) {
        List<OrderItemListVO> result = query.select(Projections.constructor(OrderItemListVO.class, orderItem))
                .from(orderItem)
                .join(orderItem.item, item)
                .where(orderItem.order.id.in(ids))
                .fetch();

        return result.stream()
                .collect(Collectors.groupingBy(OrderItemListVO::getOrderId));
    }

    private List<Long> toOrderIds(List<OrderListVO> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

    }

    private BooleanExpression usernameEq(String usernameCond) {
        if (usernameCond == null) {
            return null;
        }
        return order.user.username.eq(usernameCond);
    }


    private BooleanExpression betweenDate(LocalDateTime start, LocalDateTime end) {
        // goe >= , loe <=
        if (start == null && end == null) {
            return null;
        }
        if (start != null && end == null) {
            return order.createdAt.goe(start);
        }
        if (start == null && end != null) {
            return order.createdAt.loe(end);
        }

        return order.createdAt.goe(start).and(order.createdAt.loe(end));
    }

}
