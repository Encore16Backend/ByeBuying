package com.encore.byebuying.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum RoleB implements GrantedAuthority {
    GUEST("ROLE_GUEST", "비회원"),
    USER("ROLE_USER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자"),
    SUPER_ADMIN("ROLE_SUPER_ADMIN", "슈퍼관리자"),
    ;

    private final String authority;
    private final String description;
}