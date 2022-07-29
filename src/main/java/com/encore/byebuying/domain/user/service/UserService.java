package com.encore.byebuying.domain.user.service;

import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UserDTO;
import com.encore.byebuying.domain.user.dto.UserInfoDTO;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    String saveUser(UserDTO user);
    Collection<Location> getLocation(String username);
    UserInfoDTO getUserInfo(String username);
    User getUser(String username);
    Page<User> getUsers(Pageable pageable);

    String checkUser(String username);

    void deleteUser(String username);

    boolean existsEmail(String email);
}
