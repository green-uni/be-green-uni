package com.green.greenuni.configuration.security;

import com.green.greenuni.configuration.constants.ConstJwt;
import com.green.greenuni.configuration.model.JwtMember;
import com.green.greenuni.configuration.model.UserPrincipal;
import com.green.greenuni.configuration.util.MyCookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenManager { //인증처리 총괄
    private final ConstJwt constJwt;
    private final MyCookieUtil myCookieUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public void issue(HttpServletResponse res, JwtMember jwtMember){
        setAccessTokenInCookie(res, jwtMember);
        setRefreshTokenInCookie(res, jwtMember);
    }

    public void setAccessTokenInCookie(HttpServletResponse res, JwtMember jwtMember){
        String accessToken = jwtTokenProvider.generateAccessToken(jwtMember);
        setAccessTokenInCookie(res, accessToken);
    }
    public void setRefreshTokenInCookie(HttpServletResponse res, JwtMember jwtMember){
        String refreshToken = jwtTokenProvider.generateRefreshToken(jwtMember);
        setRefreshTokenInCookie(res, refreshToken);
    }

    // AT 쿠키 담기
    public void setAccessTokenInCookie(HttpServletResponse res, String accessToken){
        myCookieUtil.setCookie(res,
                constJwt.getAccessTokenCookieName(),
                accessToken,
                constJwt.getAccessTokenCookieValiditySeconds(),
                constJwt.getAccessTokenCookiePath()
        );
    }

    // RT 쿠키 담기
    public void setRefreshTokenInCookie(HttpServletResponse res, String refreshToken){
        myCookieUtil.setCookie(res,
                constJwt.getRefreshTokenCookieName(),
                refreshToken,
                constJwt.getRefreshTokenCookieValiditySeconds(),
                constJwt.getRefreshTokenCookiePath()
        );
    }

    // 쿠키에서 AT꺼내기
    public String getAccessTokenFromCookie(HttpServletRequest req){
        return myCookieUtil.getValue(req, constJwt.getAccessTokenCookieName());
    }


    public Authentication getAuthentication(HttpServletRequest req){
        String accessToken = getAccessTokenFromCookie(req); // AT를 쿠키에서 빼낸다.

        // 쿠키에 AT 없다면 null 반환
        if(accessToken == null){ return null; }

        //쿠키에 AT가 있다. JWT에 담았던 JwtUser객체를 다시 빼낸다.
        JwtMember jwtMember = jwtTokenProvider.getJwtMemberFromToken(accessToken);
        UserPrincipal userPrincipal = new UserPrincipal(jwtMember);

        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    // AT 삭제
    public void deleteAccessTokenInCookie(HttpServletResponse res){
        myCookieUtil.deleteCookie(res, constJwt.getAccessTokenCookieName(), constJwt.getAccessTokenCookiePath());
    }

    // RT 삭제
    public void deleteRefreshTokenInCookie(HttpServletResponse res){
        myCookieUtil.deleteCookie(res, constJwt.getRefreshTokenCookieName(), constJwt.getRefreshTokenCookiePath());
    }

    // 로그아웃할 때 AT, RT 삭제
    public void logOut(HttpServletResponse res){
        deleteAccessTokenInCookie(res);
        deleteRefreshTokenInCookie(res);
    }

}
