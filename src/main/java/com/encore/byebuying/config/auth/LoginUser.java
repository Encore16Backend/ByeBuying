package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.user.dto.UserLoginInfoDTO;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class LoginUser extends User {
    private Long userId;
    private String username;
    private RoleType roleType;

    public LoginUser(com.encore.byebuying.domain.user.User user) {
        super(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRoleType().getCode())));
        this.userId = user.getId();
        this.username = user.getUsername();
        this.roleType = user.getRoleType();
    }
}
