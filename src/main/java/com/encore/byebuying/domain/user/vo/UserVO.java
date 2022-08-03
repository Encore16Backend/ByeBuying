package com.encore.byebuying.domain.user.vo;

import com.encore.byebuying.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO { // todo : Address 와 Location, defaultLocationIdx 차이
  private long userId;
  private String username;
  private String email;

  public static UserVO valueOf(User user) {
    UserVO vo = new UserVO();
    vo.setUserId(user.getId());
    vo.setUsername(user.getUsername());
    vo.setEmail(user.getEmail());
    return vo;
  }
}
