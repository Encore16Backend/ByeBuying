package com.encore.byebuying.domain.user.dto;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserListDTO  extends PagingRequest {

  private String username;
  private String email;

  // Location
  private String zipcode;
  private String address;

  private ProviderType providerType;

  private RoleType roleType;

}
