package com.encore.byebuying.domain.user.dto;

import com.encore.byebuying.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserLoginInfoDTO {
    private String username;
    private Collection<GrantedAuthority> authorities;

    public UserLoginInfoDTO(User user) {
        this.username = user.getUsername();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRoleType().getCode()));
    }
}
