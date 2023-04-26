package com.sahmyook.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.access.expression.SecurityExpressionOperations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.logout()
                .logoutSuccessUrl("/login");
        http.authorizeRequests()
                .requestMatchers("/main/**").authenticated()
                .requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/main")
                .successHandler((request, response, authentication) -> {
                    for (GrantedAuthority auth : authentication.getAuthorities()) {
                        if ("ROLE_ADMIN".equals(auth.getAuthority())) {
                            response.sendRedirect("/admin");
                            return;
                        }
                    }
                    response.sendRedirect("/main");
                })
                .failureHandler(((request, response, exception) -> {
                    String username=request.getParameter("userId");
                    String password=request.getParameter("userPw");
                    if(username==null || password==null || username.trim().isEmpty() || password.trim().isEmpty()) {
                        response.sendRedirect(("/login"));
                    } else {
                        response.sendRedirect("/login?error");
                    }
                }));






/*
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint();
                //.userService(principalOauth2UserService);
        */
        return http.build();
    }
}