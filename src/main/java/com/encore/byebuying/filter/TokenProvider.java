package com.encore.byebuying.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.encore.byebuying.config.auth.PrincipalDetails;
import com.encore.byebuying.config.properties.AppProperties;
import com.encore.byebuying.domain.UserRefreshToken;
import com.encore.byebuying.repo.UserRefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service @Slf4j @RequiredArgsConstructor
public class TokenProvider {

    private final AppProperties appProperties;
    private final UserRefreshTokenRepo userRefreshTokenRepo;

    public Map<String, String> createToken(HttpServletRequest request, Authentication authentication) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        String role = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);
        Algorithm algorithm = Algorithm.HMAC256(appProperties.getAuth().getTokenSecret().getBytes());

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + appProperties.getAuth().getAccesstokenExpiration())) // 10분
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", role)
                .sign(algorithm); // 토큰 서명

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + appProperties.getAuth().getRefreshtokenExpiration()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        Map<String, String> token = new HashMap<>();
        token.put("access_token", access_token);
        token.put("refresh_token", refresh_token);
        token.put("role", role);

        // DB에 refresh token 저장
        UserRefreshToken userRefreshToken = new UserRefreshToken(user.getUsername(), refresh_token);
        userRefreshTokenRepo.save(userRefreshToken);

        log.info("User {} token : \n {}", user.getUsername(), token);
        return token;
    }

}
