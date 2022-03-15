package com.encore.byebuying.security;

import com.encore.byebuying.filter.CustomAuthenticationFilter;
import com.encore.byebuying.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        // login은 CustomAuthenticationFilter를 추가하여 해당 로그인한 유저를 AuthenticationManager에 추가
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http
                .cors()
                        .and()
                .csrf()
                        .disable();

        http.sessionManagement().sessionCreationPolicy(STATELESS);
        // permitAll(): 토큰 유효성 검사하지 않음
        http.authorizeRequests()
                .antMatchers("/main/**",
                        "/api/role/save", "/api/login/**", "/api/checkUser",
                        "/api/user/save", "/api/token/refresh/**", "/api/checkMail",
                        "/review/byItemid","/review/avg",
                        "/inquiry/byItemid", "/flask/**")
                .permitAll();
        http.authorizeRequests().antMatchers(DELETE, "**")
                .permitAll();
        http.authorizeRequests().antMatchers(PUT, "**")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/user/**","/review/all","/review/byUsername",
                        "/basket/byUsername","/orderHistory/byUsername",
                        "/inquiry/byUsername", "/inquiry/byUserNItemid")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/review/save", "/api/user/getUser",
                        "/basket/add","/orderHistory/add", "/inquiry/save")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/users")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/inquiry/answer")
                .hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
