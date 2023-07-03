package com.personal.parkwa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .mvcMatchers("/", "/users/**", "/css/**").permitAll()
                // 모든 인원 허용
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                // admin 권한을 가진자만 허용
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/users/login")
                .defaultSuccessUrl("/index").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/users/login")
                // 로그인 페이지 설정
                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                .logoutSuccessUrl("/index");
                // 로그아웃 설정

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
