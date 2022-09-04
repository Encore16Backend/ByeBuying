package com.encore.byebuying.domain.user.repository.user;

import com.encore.byebuying.domain.user.QLocation;
import com.encore.byebuying.domain.user.QUser;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.repository.user.param.SearchUserListParam;
import com.encore.byebuying.domain.user.vo.QUserListVO;
import com.encore.byebuying.domain.user.vo.UserListVO;
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
import org.springframework.util.StringUtils;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

  private final JPAQueryFactory query;
  private final QUser user;
  private final QLocation location;

  public UserRepositoryImpl(EntityManager em) {
    super(User.class);
    this.query = new JPAQueryFactory(em);
    this.user = new QUser("user");
    this.location = new QLocation("defaultLocation");
  }

  @Override
  public Page<UserListVO> findAll(SearchUserListParam param, Pageable pageable) {
    BooleanBuilder whereCondition = getWhereCondition(param);
    whereCondition.and(location.defaultLocation.eq(Boolean.TRUE));

    JPAQuery<UserListVO> jpaQuery = query
        .select(getUserList())
        .from(user)
        .innerJoin(location.user, user)
        .where(whereCondition)
        .orderBy(user.id.desc());

    List<UserListVO> result = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
    return new PageImpl<>(result, pageable, result.size());
  }

  private QUserListVO getUserList() {
    return new QUserListVO(
        user.id,
        user.username,
        user.email,
        user.roleType,
        user.provider
    );
  }

  private BooleanBuilder getWhereCondition(SearchUserListParam param) {
    BooleanBuilder whereCondition = new BooleanBuilder();

    if (StringUtils.hasText(param.getUsername())) {
      whereCondition.and(user.username.contains(param.getUsername()));
    }

    if (StringUtils.hasText(param.getEmail())) {
      whereCondition.and(user.email.contains(param.getEmail()));
    }

    if (StringUtils.hasText(param.getZipcode())) {
      whereCondition.and(location.zipcode.contains(param.getZipcode()));
    }

    if (StringUtils.hasText(param.getAddress())) {
      whereCondition.and(location.address.contains(param.getAddress()));
    }

    if (Objects.nonNull(param.getRoleType())) {
      whereCondition.and(user.roleType.eq(param.getRoleType()));
    }

    if (Objects.nonNull(param.getProviderType())) {
      whereCondition.and(user.provider.eq(param.getProviderType()));
    }

    if (Objects.nonNull(param.getStartDate())) {
      whereCondition.and(user.createdAt.goe(param.getStartDate()));
    }

    if (Objects.nonNull(param.getEndDate())) {
      whereCondition.and(user.createdAt.loe(param.getEndDate()));
    }

    return whereCondition;
  }
}
