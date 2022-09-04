package com.encore.byebuying.domain.user.repository.user;

import com.encore.byebuying.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
