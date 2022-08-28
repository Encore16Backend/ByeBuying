package com.encore.byebuying.config.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.user.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUser {
    private Long userId;
    private String username;
    private RoleType roleType;

    public LoginUser(DecodedJWT decodedJWT) {
        this.userId = decodedJWT.getClaim("id").as(Long.class);
        this.username = decodedJWT.getSubject();
        this.roleType = RoleType.of(decodedJWT.getClaim("role").as(String.class));
    }
}
