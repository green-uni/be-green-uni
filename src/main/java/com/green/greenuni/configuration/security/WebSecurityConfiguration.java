package com.green.greenuni.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // 로그인 유저 권한 설정하는 @PreAuthorize 사용가능하게 활성화
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //시큐리티에서 session 사용 안하겠다
                .httpBasic( hb -> hb.disable() )
                .formLogin( fl -> fl.disable() )
                .csrf(csrf -> csrf.disable())

                // 로그인 없이 접근 가능한 것만 명시
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/member/login").permitAll()
                        .requestMatchers("/api/member/logout").permitAll()
                        .requestMatchers("/api/email/**").permitAll()    // 메일 인증
                        .requestMatchers("/api/member/pw").permitAll()   // 비밀번호 재설정
                        // 나머지는 전부 로그인 필요
                        .anyRequest().authenticated()
                )
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
