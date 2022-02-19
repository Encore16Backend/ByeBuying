package com.encore.byebuying.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private HashMap<String, String> json;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // UsernamePasswordAuthenticationFilter의 obtainUsername와 obtainPassword는
    // json에 대한 요청을 처리하지 못한다.
    // 그래서 @Override하여 Content-Type이 JSON이라면 ObjectMapper를 활용하여 반환
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = super.getPasswordParameter();
        if (request.getHeader("Content-Type").equals(APPLICATION_JSON_VALUE)) {
            return json.get(password);
        }
        return request.getParameter(password);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username  = super.getUsernameParameter();
        if(request.getHeader("Content-Type").equals(APPLICATION_JSON_VALUE)) {
            return json.get(username);
        }
        return request.getParameter(username);
    }

    // 인증 시도 메서드
    // username과 password를 통해 UsernamePasswordAuthenticationToken을 만들어서
    // AuthenticationManager에 해당 유저의 Authentication을 잡는다
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(request.getHeader("Content-Type").equals(APPLICATION_JSON_VALUE)) {
            log.info("Json Login Attempt");

            ObjectMapper mapper = new ObjectMapper();
            try {
                this.json =
                        mapper.readValue(request.getReader().lines().collect(Collectors.joining()),
                                new TypeReference<HashMap<String, String>>() {
                                });
            } catch (IOException e) {
                e.printStackTrace();
                throw new AuthenticationServiceException("Request Content-Type (application/json) Parsing error");
            }
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        log.info("{} attempt to login with {}", username, password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    // 로그인 요청에 인증 완료 시 실행되는 메서드
    // 인증 성공 시 "secret"과 algorithm으로 access_token과 refresh_token을 반환
    // refresh_token의 유지 시간이 access_token의 유지 시간보다 길어야 한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10분
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm); // 토큰 서명

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 300 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        response.setContentType(APPLICATION_JSON_VALUE); // APPLICATION_JSON_VALUE = "application/json"
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
