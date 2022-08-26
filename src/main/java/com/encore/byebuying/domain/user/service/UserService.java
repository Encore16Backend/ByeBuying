package com.encore.byebuying.domain.user.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.encore.byebuying.config.properties.AppProperties;
import com.encore.byebuying.domain.common.service.UserServiceHelper;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.user.UserRefreshToken;
import com.encore.byebuying.domain.user.dto.CreateUserDTO;
import com.encore.byebuying.domain.user.repository.UserRefreshTokenRepository;
import com.encore.byebuying.domain.user.vo.UserVO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserServiceHelper userServiceHelper;
    private final AppProperties appProperties;

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

    @Transactional
    public String saveUser(CreateUserDTO dto) {
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

    public UserVO getUser(long loginUserId, long userId) {
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);
        return UserVO.valueOf(user);
    }

    public Page<User> getUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userRepository.findAll(pageable);
    }

    public Page<Location> getUserLocation(long loginUserId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("user not found"));

        // TODO: Querydsl로 사용해서 Paging 후 처리
        // TODO: 이는 Address, Location에 대한 정의가 끝난 후 처리

        return null;
    }

    public boolean checkDuplicatedUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public void deleteUser(long loginUserId, long userId) {
//        basketRepo.deleteAllByUsername(username);
//        inquiryRepo.deleteAllByUsername(username);
//        orderHistoryRepo.deleteAllByUsername(username);
//        reviewRepository.deleteAllByUsername(username);
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);
        userRepository.delete(user);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // request의 header에 "Bearer token~~~~" 형식으로 전달되기 때문에 "Bearer " 문자 제거
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                // HMAC256 활용
                Algorithm algorithm = Algorithm.HMAC256(appProperties.getAuth().getTokenSecret().getBytes());
                // verifier에 alhorithm을 적용하여 refreshToken에 대한 유효성 확인
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();
                User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new RuntimeException("user not found"));

                // DB에 저장된 refresh token과 동일한지 확인
                UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUsername(username);
                if (userRefreshToken == null || !userRefreshToken.getRefreshToken().equals(refresh_token)) {
                    throw new RuntimeException("Refresh token is missing or miss match");
                }

                // 확인되었다면 새로운 access token 발급한 후 반환
                String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + appProperties.getAuth().getAccesstokenExpiration())) // 10분
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("role", user.getRoleType().getCode())
                    .sign(algorithm); // 토큰 서명

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
