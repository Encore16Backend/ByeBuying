package com.encore.byebuying.domain.inquiry.repository;

import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.QInquiry;
import com.encore.byebuying.domain.inquiry.repository.param.SearchInquiryListParam;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import com.encore.byebuying.domain.inquiry.service.vo.QInquiryResponseVO;
import com.encore.byebuying.domain.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class InquiryRepositoryImpl extends QuerydslRepositorySupport implements InquiryRepositoryCustom {

  private final JPAQueryFactory query;
  private final QInquiry inquiry;
  private final QUser user;

  public InquiryRepositoryImpl(EntityManager em) {
    super(Inquiry.class);
    this.query = new JPAQueryFactory(em);
    this.inquiry = new QInquiry("inquiry");
    this.user = new QUser("user");
  }

  @Override
  public Page<InquiryResponseVO> findAll(SearchInquiryListParam param, Pageable pageable) {
    BooleanBuilder whereCondition = getWhereCondition(param);

    JPAQuery<InquiryResponseVO> jpaQuery = query
        .select(getInquiryList())
        .from(inquiry)
        .innerJoin(inquiry.user, user)
        .where(whereCondition)
        .orderBy(inquiry.id.desc());
    List<InquiryResponseVO> result = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
    return new PageImpl<>(result, pageable, result.size());
  }

  private QInquiryResponseVO getInquiryList() {
    return new QInquiryResponseVO(
        inquiry.id,
        inquiry.title,
        inquiry.title,
        inquiry.answer,
        inquiry.chkAnswer,
        inquiry.createdAt,
        inquiry.modifiedAt,
        user.username
    );
  }

  private BooleanBuilder getWhereCondition(SearchInquiryListParam param) {
    BooleanBuilder whereCondition = new BooleanBuilder();

    if (Objects.nonNull(param.getUserId())) {
      whereCondition.and(user.id.eq(param.getUserId()));
    }

    if (Objects.nonNull(param.getStartDate())) {
      whereCondition.and(inquiry.modifiedAt.goe(param.getStartDate()));
    }

    if (Objects.nonNull(param.getEndDate())) {
      whereCondition.and(inquiry.modifiedAt.loe(param.getEndDate()));
    }

    return whereCondition;
  }
}
