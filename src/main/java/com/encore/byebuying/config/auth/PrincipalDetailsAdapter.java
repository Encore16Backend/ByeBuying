package com.encore.byebuying.config.auth;

import com.encore.byebuying.domain.user.dto.UserLoginInfoDTO;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class PrincipalDetailsAdapter extends User {

    private UserLoginInfoDTO userLoginInfo;

    public PrincipalDetailsAdapter(com.encore.byebuying.domain.user.User user) {
        super(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRoleType().getCode())));
        this.userLoginInfo = new UserLoginInfoDTO(user);
    }


}
