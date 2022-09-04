package com.encore.byebuying.domain.user.vo;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDefaultVO {

  protected long userId;
  protected String username;
  protected String email;

  protected RoleType roleType;
  protected ProviderType providerType;

}
