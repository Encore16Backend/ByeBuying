package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.code.RoleType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUser {
    private String username;
    private RoleType roleType;

    public LoginUser(String username, RoleType roleType) {
        this.username = username;
        this.roleType = roleType;
    }
}
