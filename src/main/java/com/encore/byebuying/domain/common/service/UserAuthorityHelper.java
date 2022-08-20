package com.encore.byebuying.domain.common.service;

import static com.encore.byebuying.domain.code.RoleType.ADMIN;
import static com.encore.byebuying.domain.code.RoleType.SUPER_ADMIN;

import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class UserAuthorityHelper {

  private final UserRepository userRepository;

  public void checkAuthorityValidation(User entityUser, User requestUser) {
    if (!(entityUser.equals(requestUser) ||
        requestUser.getRoleType().equals(ADMIN) || requestUser.getRoleType().equals(SUPER_ADMIN))) {
      throw new RuntimeException("Not Authority");
    }
  }

}
