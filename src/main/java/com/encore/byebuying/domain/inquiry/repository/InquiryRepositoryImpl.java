package com.encore.byebuying.domain.inquiry.repository;

import static org.springframework.util.StringUtils.hasText;

import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.QInquiry;
import com.encore.byebuying.domain.inquiry.controller.dto.SearchInquiryDTO;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import com.encore.byebuying.domain.inquiry.service.vo.QInquiryResponseVO;
import com.encore.byebuying.domain.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
  public Page<InquiryResponseVO> findAll(SearchInquiryDTO dto, Pageable pageable) {
    BooleanBuilder whereCondition = getWhereCondition(dto);

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

  private BooleanBuilder getWhereCondition(SearchInquiryDTO dto) {
    BooleanBuilder whereCondition = new BooleanBuilder();

    if (hasText(dto.getUsername())) {
      whereCondition.and(user.username.eq(dto.getUsername()));
    }

    return whereCondition;
  }
}
