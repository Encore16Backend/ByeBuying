package com.encore.byebuying.service;

import com.encore.byebuying.domain.Role;
import com.encore.byebuying.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user); // SignUP
    Role saveRole(Role role); // 새로운 권한 생성 시
    // 사용자에게 권한 부여
    void addRoleToUser(String username, String roleName);
    // 사용자
    User getUser(String username);
    // 사용자들
    List<User> getUsers();
}
