package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.code.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter @Setter @AllArgsConstructor @RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final long id;
    private final String username;
    private final String password;
    private final ProviderType providerType;
    private final RoleType roleType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static PrincipalDetails create(User user) { // 일반 회원 생성
        return new PrincipalDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getProvider(),
                RoleType.USER,
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
        );
    }

    public static PrincipalDetails create(User user, Map<String, Object> attributes) { // OAuth 회원 생성
        PrincipalDetails principalDetails = create(user);
        principalDetails.setAttributes(attributes);
        return principalDetails;
    }



}
