package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.code.RoleType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUser {
    private Long id;
    private String username;
    private RoleType roleType;

    public LoginUser(Long id, String username, RoleType roleType) {
        this.id = id;
        this.username = username;
        this.roleType = roleType;
    }
}
