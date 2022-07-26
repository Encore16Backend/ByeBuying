package com.encore.byebuying.domain.user.service;

import com.encore.byebuying.config.Exception.ResourceNotFoundException;
import com.encore.byebuying.domain.order.repository.OrderRepository;
import com.encore.byebuying.domain.review.repository.ReviewRepository;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.user.dto.UserDTO;
import com.encore.byebuying.domain.user.dto.UserInfoDTO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final BasketRepository basketRepository;
//    private final InquiryRepository inquiryRepository;
    private final OrderRepository orderHistoryRepo;
    private final ReviewRepository reviewRepository;

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
    @Transactional
    public String saveUser(UserDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseGet(() ->
                User.initUser().dto(dto).provider(ProviderType.LOCAL).build());

        if (user.getPassword() != null || !user.getPassword().isEmpty()) { // 회원정보수정, 회원가입 공통
            user.encodePassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getId() == null) { // 회원가입
            log.info("Saving new user {} to the database", user.getUsername());
            userRepository.save(user);
        } else { // 회원정보수정
            user.changeUser(user);
        }
        return user.getUsername();
    }

    @Override
    public Collection<Location> getLocation(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username).orElseThrow(() ->
            new ResourceNotFoundException(username, "username", User.class))
            .getLocations();
    }

    @Override
    public UserInfoDTO getUserInfo(String username) {
        log.info("Fetching user {}", username);
        User user = userRepository.findByUsername(username).orElseThrow(() ->
            new ResourceNotFoundException(username, "username", User.class));
        return new UserInfoDTO(user.getUsername(), user.getEmail());
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
            new ResourceNotFoundException(username, "username", User.class));
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userRepository.findAll(pageable);
    }

    @Override
    public String checkUser(String username) {
        return userRepository.existsByUsername(username) ? "FAIL" : "SUCCESS";
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
//        basketRepo.deleteAllByUsername(username);
//        inquiryRepo.deleteAllByUsername(username);
        orderHistoryRepo.deleteAllByUsername(username);
//        reviewRepository.deleteAllByUsername(username);
        userRepository.deleteByUsername(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
