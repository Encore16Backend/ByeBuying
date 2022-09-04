package com.encore.byebuying.domain.user.repository.user;

import com.encore.byebuying.domain.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUsername(String username);
    UserRefreshToken save(UserRefreshToken userRefreshToken);
}
