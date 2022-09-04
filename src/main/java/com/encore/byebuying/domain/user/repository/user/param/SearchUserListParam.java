package com.encore.byebuying.domain.user.repository.user.param;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.GetUserListDTO;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserListParam {

  private String username;
  private String email;

  // Location
  private String zipcode;
  private String address;

  private ProviderType providerType;

  private RoleType roleType;

  // 회원가입 날짜 조건
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public static SearchUserListParam valueOf(GetUserListDTO dto) {
    SearchUserListParam param = new SearchUserListParam();

    param.setUsername(dto.getUsername());
    param.setEmail(dto.getEmail());
    param.setZipcode(dto.getZipcode());
    param.setAddress(dto.getAddress());
    param.setProviderType(dto.getProviderType());
    param.setRoleType(dto.getRoleType());

    param.setStartDate(dto.getStartDate());
    param.setEndDate(dto.getEndDate());

    return param;
  }
}
