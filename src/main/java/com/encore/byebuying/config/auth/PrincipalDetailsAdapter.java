package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.code.RoleType;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class PrincipalDetailsAdapter extends User {

    private com.encore.byebuying.domain.user.User user;

    public PrincipalDetailsAdapter(com.encore.byebuying.domain.user.User user) {
        super(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRoleType().getCode())));
        this.user = user;
    }


}
