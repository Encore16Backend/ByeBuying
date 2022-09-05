package com.encore.byebuying.domain.user.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.encore.byebuying.config.properties.AppProperties;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.common.service.UserServiceHelper;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.UserRefreshToken;
import com.encore.byebuying.domain.user.dto.GetLocationDTO;
import com.encore.byebuying.domain.user.dto.GetUserListDTO;
import com.encore.byebuying.domain.user.dto.UpdateLocationDTO;
import com.encore.byebuying.domain.user.dto.UpdateUserDTO;
import com.encore.byebuying.domain.user.repository.location.LocationRepository;
import com.encore.byebuying.domain.user.repository.location.param.SearchLocationListParam;
import com.encore.byebuying.domain.user.repository.user.UserRefreshTokenRepository;
import com.encore.byebuying.domain.user.repository.user.UserRepository;
import com.encore.byebuying.domain.user.repository.user.param.SearchUserListParam;
import com.encore.byebuying.domain.user.vo.LocationVO;
import com.encore.byebuying.domain.user.vo.UserDetailVO;
import com.encore.byebuying.domain.user.vo.UserListVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
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

    /**
     * User Service
     */

    @Transactional
    public UserDetailVO saveUser(Long loginUserId, UpdateUserDTO dto) {
        if (StringUtils.hasText(dto.getPassword())) { // 회원정보수정, 회원가입 공통
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        User user;
        if (loginUserId == null) { // 회원가입
            log.info("Saving new user {} to the database", dto.getUsername());
            // 아이디 중복 체크
            User duplicatedUser = userRepository.findByUsername(dto.getUsername())
                .orElse(null);
            if (duplicatedUser != null) {
                throw new RuntimeException("username is duplicated");
            }
        } else { // 회원정보수정
            if (dto.getUserId() == null) {
                throw new RuntimeException("user id required");
            }

            user = userServiceHelper
                .checkLoginUserRequestUserEquals(loginUserId, dto.getUserId());
            log.info("Update user : {}", user.getUsername());
        }

        user = User.updateUser()
            .dto(dto)
            .provider(ProviderType.LOCAL)
            .build();
        userRepository.save(user);
        return UserDetailVO.valueOf(user);
    }

    public UserDetailVO getUser(long loginUserId, long userId) {
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);
        return UserDetailVO.valueOf(user);
    }

    public Page<UserListVO> getUserList(GetUserListDTO dto) {
        return userRepository
            .findAll(SearchUserListParam.valueOf(dto), dto.getPageRequest());
    }

    public boolean checkDuplicatedUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public void deleteUser(long loginUserId, long userId) {
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);

        List<Location> locations = user.getLocations();
        locations.forEach(location -> locationRepository.delete(location));

        userRepository.delete(user);
    }

    /**
     * Location Service
     */

    public Page<LocationVO> getUserLocationList(long loginUserId, long userId, Pageable pageable) {
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);

        return locationRepository.findAll(SearchLocationListParam.valueOf(user.getId()), pageable);
    }

    public LocationVO getUserLocation(long loginUserId, long userId, GetLocationDTO dto) {
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);

        Location location;
        if (dto.isDefaultLocation()) {
            location = locationRepository.findByDefaultLocationAndUser(dto.isDefaultLocation(), user)
                .orElse(Location.createLocation());
        } else {
            location = locationRepository.findByIdAndUser(dto.getLocationId(), user)
                .orElseThrow(() -> new RuntimeException("리소스가 없거나 권한이 없음"));
        }

        return LocationVO.valueOf(location);
    }

    @Transactional
    public LocationVO updateUserLocation(long loginUserId, long userId, UpdateLocationDTO dto) {
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);

        // 새로 생성하는 배송지를 기본 배송지로서 저장하려고 할 때
        if (dto.getDefaultLocation()) {
            // 기존 기존 배송지가 있는지 확인
            Location defaultLocation = locationRepository.findByDefaultLocationAndUser(true, user)
                .orElse(null);
            // 기존 기본 배송지 해제
            if (defaultLocation != null) {
                defaultLocation.setDefaultLocation(false);
                locationRepository.save(defaultLocation);
            }
        }
        Location location;

        if (dto.getLocationId() != null) { // 수정작업
            location = locationRepository.findByIdAndUser(dto.getLocationId(), user)
                .orElseThrow(() -> new RuntimeException("리소스가 없거나 권한이 없음"));
            location = Location.updateLocation(location, dto);
        } else {
            location = Location.createLocation(dto, user);
        }

        locationRepository.save(location);

        return LocationVO.valueOf(location);
    }

    @Transactional
    public void deleteUserLocation(long loginUserId, long userId, long locationId) {
        User user = userServiceHelper
            .checkLoginUserRequestUserEquals(loginUserId, userId);

        Location location = locationRepository.findByIdAndUser(locationId, user)
            .orElseThrow(() -> new RuntimeException("리소스가 없거나 권한이 없음"));

        locationRepository.delete(location);
    }

    /**
     * Token Service
     */

    // TODO: 2022/09/05 RefreshToken 서비스 수정 및 API 요청으로 Refresh 할 것인가에 대한 고민 필요
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
                    .withClaim("id", user.getId())
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
