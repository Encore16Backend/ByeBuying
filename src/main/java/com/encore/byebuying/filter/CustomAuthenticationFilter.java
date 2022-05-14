package com.encore.byebuying.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.encore.byebuying.config.auth.PrincipalDetails;
import com.encore.byebuying.domain.UserRefreshToken;
import com.encore.byebuying.repo.UserRefreshTokenRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j @RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private HashMap<String, String> json;

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

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.

        return authenticationManager.authenticate(authenticationToken);
    }

    // 로그인 요청에 인증 완료 시 실행되는 메서드
    // 인증 성공 시 "secret"과 algorithm으로 access_token과 refresh_token을 반환
    // refresh_token의 유지 시간이 access_token의 유지 시간보다 길어야 한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE); // APPLICATION_JSON_VALUE = "application/json"
        Map<String, String> token = tokenProvider.createToken(request, authentication);
        new ObjectMapper().writeValue(response.getOutputStream(), token); // 토큰 전달
    }
}
