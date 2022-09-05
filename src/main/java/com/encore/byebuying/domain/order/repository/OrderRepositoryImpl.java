package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.item.QItem;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.QOrder;
import com.encore.byebuying.domain.order.QOrderItem;
import com.encore.byebuying.domain.order.dto.OrderItemListVO;
import com.encore.byebuying.domain.order.dto.OrderListVO;
import com.encore.byebuying.domain.user.QUser;
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

@Repository
@Transactional(readOnly = true)
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

     final JPAQueryFactory query;
    private final QOrder order;
    private final QUser user;
    private final QOrderItem orderItem;
    private final QItem item;

    public OrderRepositoryImpl(EntityManager em) {
        super(Order.class);
        this.query = new JPAQueryFactory(em);
        this.order = new QOrder("order");
        this.user = new QUser("user");
        this.orderItem = new QOrderItem("orderItem");
        this.item = new QItem("item");
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

    // TODO: 2022-09-06 할거임
    @Override
    public Page<OrderListVO> findByCreatedAtBetweenAndUser(Pageable pageable, LocalDateTime start, LocalDateTime end, String username) {
        return null;
    }

    private BooleanExpression usernameEq(String usernameCond) {
        if (usernameCond == null) {
            return null;
        }
        return order.user.username.eq(usernameCond);
    }

/*
    private BooleanExpression betweenDate() {

   }
 */
}
