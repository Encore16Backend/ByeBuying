package com.encore.byebuying.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDTO { // todo : Address 와 Location, defaultLocationIdx 차이
  private String username;
  private String email;

}
