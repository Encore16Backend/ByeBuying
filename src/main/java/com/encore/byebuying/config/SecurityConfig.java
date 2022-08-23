package com.encore.byebuying.config;

import com.encore.byebuying.config.Exception.RestAuthenticationEntryPoint;
import com.encore.byebuying.config.auth.PrincipalUserDetailsService;
import com.encore.byebuying.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.encore.byebuying.config.oauth.PrincipalOAuth2UserService;
import com.encore.byebuying.config.oauth.handler.OAuth2AuthenticationFailHandler;
import com.encore.byebuying.config.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.encore.byebuying.config.properties.AppProperties;
import com.encore.byebuying.filter.CustomAuthenticationFilter;
import com.encore.byebuying.filter.CustomAuthorizationFilter;
import com.encore.byebuying.filter.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PrincipalUserDetailsService userDetailsService;
    private final PrincipalOAuth2UserService oAuth2UserService;
    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
//        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf()
            .disable();

        http
            .headers().frameOptions().disable()
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint()); // 인증 또는 인가에 대한 exception 핸들링 클래스 정의

        // 세션 매니저 무상태성으로 변경 -> 세션 사용 X
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        // permitAll(): 토큰 유효성 검사하지 않음
        http.authorizeRequests()
                .antMatchers("/main/**",
                        "/api/role/save", "/api/login/**", "/api/checkUser",
                        "/api/user/save", "/api/token/refresh/**", "/api/checkMail","/api/user/update/admin",
                        "/api/role/save", "/login/**", "/oauth2/**", "/api/checkUser",
                        "/api/user/save", "/api/token/refresh/**", "/api/checkMail",
                        "/review/byItemid","/review/avg",
                        "/inquiry/byItemid", "/flask/retrieval")
                .permitAll();
        http.authorizeRequests().antMatchers(DELETE, "**")
                .permitAll();
        http.authorizeRequests().antMatchers(PUT, "**")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/user/**","/review/all","/review/byUsername", "/api/orders/**",
                        "/basket/byUsername","/orderHistory/getOrderHistories", "/inquiry/byUserNItemid", "/flask/recommend")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/review/save", "/api/user/getUser",
                        "/basket/add","/orderHistory/add", "/inquiry/save")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/users")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/users")
                .permitAll();
        http.authorizeRequests().antMatchers(PUT, "/inquiry/answer")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/message/send")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/role/add-to-user")

                .hasAnyAuthority("ROLE_SUPER_ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
        http.oauth2Login()
            .authorizationEndpoint()
            .baseUri("/oauth2/authorization")
            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
            .and()
            .redirectionEndpoint()
            .baseUri("/*/oauth2/code/*")
            .and()
            .userInfoEndpoint()
            .userService(oAuth2UserService)
            .and()
            .successHandler(new OAuth2AuthenticationSuccessHandler(tokenProvider, appProperties, cookieAuthorizationRequestRepository()))
            .failureHandler(new OAuth2AuthenticationFailHandler(cookieAuthorizationRequestRepository()));
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), tokenProvider));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // Swagger
    @Override
    public void configure(WebSecurity web) { // http://127.0.0.1:8081/swagger-ui/index.html
        web.ignoring()
            .antMatchers("/v3/api-docs", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html",
                "/webjars/**", "/swagger/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
