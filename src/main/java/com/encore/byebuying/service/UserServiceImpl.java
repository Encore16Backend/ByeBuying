package com.encore.byebuying.service;

import com.encore.byebuying.domain.User;
import com.encore.byebuying.domain.ProviderType;
import com.encore.byebuying.domain.Role;
import com.encore.byebuying.requestDto.UserFormRequest;
import com.encore.byebuying.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final BasketRepo basketRepo;
    private final InquiryRepo inquiryRepo;
    private final OrderRepo orderHistoryRepo;
    private final ReviewRepo reviewRepo;

//    // 인증 부여 시 Spring Security 에서 해당 유저에 대한 정보를 찾을 수 있도록 해야함
//    // 그를 위해 UserDetails를 구현하여 Spring Security의 User로 반환
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepo.findByUsername(username);
//        if (user == null){
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//        } else {
//            log.info("User found in the database: {}", username);
//        }
//        // 권한 가져옴
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
////        user.getRoles().forEach(role -> {
////            authorities.add(new SimpleGrantedAuthority(role.getName()));
////        });
//        authorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//    }

    @Override
    public User saveUser(UserFormRequest userFormRequest) {
        log.info("Saving new user {} to the database", userFormRequest.getUsername());
        User user = User.builder()
                        .username(userFormRequest.getUsername())
                        .password(passwordEncoder.encode(userFormRequest.getPassword()))
                        .email(userFormRequest.getEmail())
                        .locations(userFormRequest.getLocations())
                        .role(Role.USER)
                        .provider(ProviderType.LOCAL).build();
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }


    @Override
    public User getUser(String userName) {
        log.info("Fetching user {}", userName);
        return userRepo.findByUsername(userName).orElse(null);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userRepo.findAll(pageable);
    }

    @Override
    public boolean checkUser(String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        return user == null;
    }

    @Override
    public void deleteUser(String username) {
        basketRepo.deleteAllByUsername(username);
        inquiryRepo.deleteAllByUsername(username);
        orderHistoryRepo.deleteAllByUsername(username);
        reviewRepo.deleteAllByUsername(username);
        userRepo.deleteByUsername(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
