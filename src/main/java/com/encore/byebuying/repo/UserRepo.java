package com.encore.byebuying.repo;

import com.encore.byebuying.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User deleteByUsername(String username);
}
