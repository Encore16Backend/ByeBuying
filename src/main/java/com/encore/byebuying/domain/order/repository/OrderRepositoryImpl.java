package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.vo.OrderItemListVO;
import com.encore.byebuying.domain.order.vo.OrderListVO;
import com.encore.byebuying.domain.order.vo.QOrderItemListVO;
import com.encore.byebuying.domain.order.vo.QOrderListVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

@Slf4j
@Repository
@Transactional(readOnly = true)
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

     private final JPAQueryFactory query;

    public OrderRepositoryImpl(EntityManager em) {
        super(Order.class);
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderListVO> findByCreatedAtBetweenAndUser(Pageable pageable, LocalDateTime start, LocalDateTime end, Long userId) {
        JPAQuery<OrderListVO> jpaQuery = query
                .select(
                        getOrderList()
                ).from(order)
                .where(
                        userIdEq(userId),
                        betweenDate(start, end)
                );


        return getOrderListVOS(pageable, jpaQuery);
    }

    private Page<OrderListVO> getOrderListVOS(Pageable pageable, JPAQuery<OrderListVO> jpaQuery) {
        List<OrderListVO> result = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
        log.info("확인 :: {}", result);
        Map<Long, List<OrderItemListVO>> orderItemMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o -> o.setItems(orderItemMap.get(o.getOrderId())));

        return new PageImpl<>(result, pageable, result.size());
    }

    private Map<Long, List<OrderItemListVO>> findOrderItemMap(List<Long> ids) {
        List<OrderItemListVO> result =
                query.select(
                    getOrderItemList()
                )
                .from(orderItem)
                .join(orderItem.item, item)
                .join(orderItem.order, order)
                .where(order.id.in(ids))
                .fetch();

        return result.stream()
                .collect(Collectors.groupingBy(OrderItemListVO::getOrderId));
    }

    private List<Long> toOrderIds(List<OrderListVO> result) {
        return result.stream()
                .map(OrderListVO::getOrderId)
                .collect(Collectors.toList());
    }

    private BooleanExpression userIdEq(Long userIdCond) {
        if (userIdCond == null) {
            return null;
        }
        return order.user.id.eq(userIdCond);
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

    private QOrderListVO getOrderList() {
        return new QOrderListVO(
                order.id,
                order.address,
                order.createdAt,
                order.orderType.stringValue()
        );
    }

    private QOrderItemListVO getOrderItemList() {
        return new QOrderItemListVO(
                order.id,
                item.name,
                item.price,
                orderItem.count,
                orderItem.orderPrice
        );
    }
}
