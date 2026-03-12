package com.green.greenuni.configuration.security;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// request, response는 무조건 filter를 거치니 그때 할 작업 진행
// 쿠키안에 AT가 저장되어있는지 확인. 저장되어있으면 시큐리티 인증처리
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("req-uri: {}", request.getRequestURI()); //요청 주소가 로그에 출력

        // 쿠키에 AT가 없다면 null 리턴, 쿠키에 AT가 있다면 주소값 리턴
        Authentication authentication = jwtTokenManager.getAuthentication(request);
        log.info("authentication: {}", authentication);
        try {
            if (authentication != null) {  //로그인 상태
                SecurityContextHolder.getContext().setAuthentication(authentication); //시큐리티 인증처리 완료
            } else {
                request.setAttribute("exception", new MalformedJwtException("토큰 확인"));
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        //다음 필터에게 req, res 전달
        filterChain.doFilter(request, response);
    }
}
