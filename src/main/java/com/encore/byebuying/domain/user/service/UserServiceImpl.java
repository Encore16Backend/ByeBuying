package com.encore.byebuying.domain.user.service;

import com.encore.byebuying.domain.basket.repository.BasketRepository;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.order.repository.OrderRepository;
import com.encore.byebuying.domain.review.repository.ReviewRepository;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.user.dto.UserSaveDTO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BasketRepository basketRepository;
    private final InquiryRepository inquiryRepository;
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
    public User saveUser(UserSaveDTO dto) {
        log.info("Saving new user {} to the database", dto.getUsername());
        User user = new User(dto);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public User getUser(String userName) {
        log.info("Fetching user {}", userName);
        return userRepository.findByUsername(userName).orElse(null);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean checkUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user == null;
    }

    @Override
    public void deleteUser(String username) {
//        basketRepo.deleteAllByUsername(username);
//        inquiryRepo.deleteAllByUsername(username);
        orderHistoryRepo.deleteAllByUsername(username);
        reviewRepository.deleteAllByUsername(username);
        userRepository.deleteByUsername(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
