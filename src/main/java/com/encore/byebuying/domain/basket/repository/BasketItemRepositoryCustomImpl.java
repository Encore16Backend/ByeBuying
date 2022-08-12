package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.QBasketItem;
import com.encore.byebuying.domain.basket.dto.BasketItemSearchDTO;
import com.encore.byebuying.domain.basket.service.vo.BasketItemRequestVO;
import com.encore.byebuying.domain.basket.service.vo.BasketItemResponseVO;
import com.encore.byebuying.domain.basket.service.vo.QBasketItemResponseVO;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.item.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

public class BasketItemRepositoryCustomImpl extends QuerydslRepositorySupport implements BasketItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QBasketItem basketItem;
    private final QItem item;

    public BasketItemRepositoryCustomImpl(EntityManager em) {
        super(BasketItem.class);
        this.jpaQueryFactory = new JPAQueryFactory(em);
        this.basketItem = new QBasketItem("basketItem");
        this.item = new QItem("item");
    }

    @Override
    public Page<BasketItemResponseVO> findAll(BasketItemSearchDTO basketItemSearchDTO, BasketItemRequestVO vo) {
        BooleanBuilder whereCondition = getWhereCondition(basketItemSearchDTO, vo);

        JPAQuery<BasketItemResponseVO> contents = jpaQueryFactory.select(getBasketItems())
                 .from(basketItem)
                .innerJoin(basketItem.item, item)
                .where(whereCondition);

        List<BasketItemResponseVO> result = getQuerydsl().applyPagination(basketItemSearchDTO.getPageRequest()
                , contents).fetch();

        return new PageImpl<>(result, basketItemSearchDTO.getPageRequest(), result.size());
    }

    private QBasketItemResponseVO getBasketItems() {
        return new QBasketItemResponseVO(
                basketItem.id,
                basketItem.count,
                basketItem.item
        );
    }

    private BooleanBuilder getWhereCondition(BasketItemSearchDTO dto, BasketItemRequestVO vo) {
        BooleanBuilder whereCondition = new BooleanBuilder();

        whereCondition.and(basketItem.basket.id.eq(vo.getBasket_id()))
                .and(likeItemName(dto.getItemName()))
                .and(hasDate(dto.getStartDate(), dto.getEndDate()));

        return whereCondition;
    }


    private BooleanExpression likeItemName(String itemName) {
        if (ObjectUtils.isEmpty(itemName)) {
            return null;
        }
        return basketItem.item.name.contains(itemName);
    }

    private BooleanExpression hasDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (ObjectUtils.isEmpty(startDate) || ObjectUtils.isEmpty(endDate)) {
            return null;
        } // 시작, 종료날짜가 다 존재해야 검색
        return basketItem.modifiedAt.between(startDate, endDate);
    }









}
