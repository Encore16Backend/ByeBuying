package com.encore.byebuying.domain.user.service;

import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UserSaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    User saveUser(UserSaveDTO user); // SignUP
    User updateUser(User user); // update
//    Role saveRole(Role role); // 새로운 권한 생성 시
    // 사용자에게 권한 부여
//    void addRoleToUser(String username, String roleName);
    // 사용자
    User getUser(String username);
    // 사용자들
    Page<User> getUsers(Pageable pageable); // 전체 사용자

    boolean checkUser(String username);

    @Transactional
    void deleteUser(String username);

    boolean existsEmail(String email);
}
