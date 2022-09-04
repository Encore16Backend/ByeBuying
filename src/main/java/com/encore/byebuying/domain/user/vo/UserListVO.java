package com.encore.byebuying.domain.user.vo;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserListVO extends UserDefaultVO {

  @QueryProjection
  public UserListVO(long userId, String username, String email, RoleType roleType,
      ProviderType providerType) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.roleType = roleType;
    this.providerType = providerType;
  }

}
