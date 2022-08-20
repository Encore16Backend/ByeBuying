package com.encore.byebuying.domain.basket.repository;

import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.basket.QBasketItem;
import com.encore.byebuying.domain.basket.service.vo.BasketItemVO;
import com.encore.byebuying.domain.basket.service.vo.QBasketItemVO;
import com.encore.byebuying.domain.basket.service.vo.SearchBasketItemListParam;
import com.encore.byebuying.domain.item.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import static org.springframework.util.StringUtils.hasText;

//public class BasketItemRepositoryCustomImpl extends QuerydslRepositorySupport implements BasketItemRepositoryCustom {

//    private final JPAQueryFactory jpaQueryFactory;
//    private final QBasketItem basketItem;
//    private final QItem item;
//
//    public BasketItemRepositoryCustomImpl(EntityManager em) {
//        super(BasketItem.class);
//        this.jpaQueryFactory = new JPAQueryFactory(em);
//        this.basketItem = new QBasketItem("basketItem");
//        this.item = new QItem("item");
//    }
//
//    @Override
//    public Page<BasketItemVO> findAll(SearchBasketItemListParam param, Pageable pageable) {
//        BooleanBuilder whereCondition = getWhereCondition(param);
//
//        JPAQuery<BasketItemVO> contents = jpaQueryFactory.select(getBasketItems())
//                .from(basketItem)
//                .innerJoin(basketItem.item, item)
//                .where(whereCondition);
//
//        List<BasketItemVO> result = getQuerydsl().applyPagination(pageable
//                , contents).fetch();
//
//        return new PageImpl<>(result, pageable, result.size());
//    }
//
//    private QBasketItemVO getBasketItems() {
//        return new QBasketItemVO(
//                basketItem.id,
//                basketItem.count,
//                basketItem.item
//        );
//    }
//
//    private BooleanBuilder getWhereCondition(SearchBasketItemListParam param) {
//        BooleanBuilder whereCondition = new BooleanBuilder();
//
//        // 동적할당 -> Builder로 조건절 만들기
//        // BooleandExpression은 join조건때 사용
//        if (Objects.nonNull(param.getBasketId())){
//            whereCondition.and(basketItem.basket.id.eq(param.getBasketId()));
//        }
//        if (hasText(param.getItemName())){
//            whereCondition.and(basketItem.item.name.contains(param.getItemName()));
//        }
//        if (Objects.nonNull(param.getStartDate())){
//            whereCondition.and(basketItem.item.modifiedAt.goe(param.getStartDate()));
//        }
//        if (Objects.nonNull(param.getEndDate())){
//            whereCondition.and(basketItem.item.modifiedAt.loe(param.getEndDate()));
//        }
//
//        return whereCondition;
//    }

//}
