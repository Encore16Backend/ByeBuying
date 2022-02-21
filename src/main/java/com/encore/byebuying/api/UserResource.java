package com.encore.byebuying.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.encore.byebuying.repo.RoleRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.encore.byebuying.domain.Role;
import com.encore.byebuying.domain.User;
import com.encore.byebuying.service.UserService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users") // 관리자 유저들 확인
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/getUser") // 회원 확인용 - 수정 or 삭제
    public ResponseEntity<User> getUser(@RequestBody User userinfo) {
        User user = userService.getUser(userinfo.getUsername());
        System.out.println(userinfo.getUsername()+" "+userinfo.getPassword());
        if (passwordEncoder.matches(userinfo.getPassword(), user.getPassword())){
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.badRequest().body(user);
    }

    @GetMapping("/checkUser") // 아이디 중복 검사 확인
    public ResponseEntity<?> checkUser(
            @RequestParam(defaultValue = "", value = "username") String username) {
        boolean check = userService.checkUser(username);
        if (check) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("FAIL", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user/delete") // 토큰 필요, 삭제 전 /api/user/getUser 에서 토큰 및 비밀번호 확인
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getUsername());
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/user/update") // 토큰 필요
    public ResponseEntity<?> updateUser(@RequestBody UserForm userForm) {
        User user = userService.getUser(userForm.getUsername());
        if (user == null){
            return new ResponseEntity<>("FAIL", HttpStatus.OK);
        }
        if (userForm.getPassword() != null) // 비밀번호도 수정될 때
            user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setStyle(userForm.getStyle());
        user.setLocation(userForm.getLocation());
        userService.saveUser(user);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody UserForm userForm) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/user/save").toUriString());

        User user = userForm.toEntity();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepo.findByName("ROLE_USER"));

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUserid(), form.getRolename());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // request의 header에 "Bearer token~~~~" 형식으로 전달되기 때문에 "Bearer " 문자 제거
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                // HMAC256 활용
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                // verifier에 alhorithm을 적용하여 refreshToken에 대한 유효성 확인
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                // 확인되었다면 새로운 access token 발급한 후 반환
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

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

@Data
class RoleToUserForm {
    private String userid;
    private String rolename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UserForm {
    private String username;
    private String password;
    private String email;
    private String location;
    private int style;

    public User toEntity(){
        return User.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .location(this.location)
                .style(this.style)
                .roles(new ArrayList<>())
                .build();
    }
}
