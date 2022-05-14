package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.User;
import com.encore.byebuying.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Slf4j
public class PrincipalUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException("User not found in the database")
        );
        log.info("User found in the database: {}", username);
        return PrincipalDetails.create(user);
    }
}
