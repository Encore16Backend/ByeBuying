package com.encore.byebuying.repo;

import com.encore.byebuying.domain.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepo extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUsername(String username);
    UserRefreshToken save(UserRefreshToken userRefreshToken);
}
