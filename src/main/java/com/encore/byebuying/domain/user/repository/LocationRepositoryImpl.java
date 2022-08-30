package com.encore.byebuying.domain.user.repository;

import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.QLocation;
import com.encore.byebuying.domain.user.QUser;
import com.encore.byebuying.domain.user.repository.param.SearchLocationListParam;
import com.encore.byebuying.domain.user.vo.LocationVO;
import com.encore.byebuying.domain.user.vo.QLocationVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class LocationRepositoryImpl extends QuerydslRepositorySupport implements LocationRepositoryCustom {

  private final JPAQueryFactory query;
  private final QLocation location;
  private final QUser user;

  public LocationRepositoryImpl(EntityManager em) {
    super(Location.class);
    this.query = new JPAQueryFactory(em);
    this.location = new QLocation("location");
    this.user = new QUser("user");
  }

  @Override
  public Page<LocationVO> findAll(SearchLocationListParam param, Pageable pageable) {
    BooleanBuilder whereCondition = new BooleanBuilder();
    whereCondition.and(user.id.eq(param.getUserId())); // user id는 필수

    JPAQuery<LocationVO> jpaQuery = query
        .select(getLocationList())
        .from(location)
        .innerJoin(location.user, user)
        .where(whereCondition)
        .orderBy(location.defaultLocation.desc(), location.id.desc());

    List<LocationVO> result = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
    return new PageImpl<>(result, pageable, result.size());
  }

  private QLocationVO getLocationList() {
    return new QLocationVO(
        location.id,
        location.name,
        location.zipcode,
        location.address,
        location.detailAddress,
        location.defaultLocation,
        location.requestDeliveryType
    );
  }

}
