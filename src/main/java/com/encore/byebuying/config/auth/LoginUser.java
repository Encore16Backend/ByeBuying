package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.code.RoleType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUser {
    private Long userId;
    private String username;
    private RoleType roleType;

    public LoginUser(Long userId, String username, RoleType roleType) {
        this.userId = userId;
        this.username = username;
        this.roleType = roleType;
    }
}
