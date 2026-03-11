package com.green.greenuni.configuration.security;

import com.green.greenuni.configuration.constants.ConstJwt;
import com.green.greenuni.configuration.model.JwtMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final ObjectMapper objectMapper;
    private final ConstJwt constJwt;
    private final SecretKey secretKey;

    public JwtTokenProvider(ObjectMapper objectMapper, ConstJwt constJwt) {
        this.objectMapper = objectMapper;
        this.constJwt = constJwt;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(constJwt.getSecretKey()));

        log.info("constJwt: {}", this.constJwt);
    }

    // 15분 만료 토큰 생성
    public String generateAccessToken(JwtMember jwtMember){
        return generateToken(jwtMember , constJwt.getAccessTokenValidityMilliseconds());
    }

    // 15일 만료 토큰 생성
    public String generateRefreshToken(JwtMember jwtMember){
        return generateToken(jwtMember , constJwt.getRefreshTokenValidityMilliseconds());
    }

    // JWT 문자열 만드는 메소드. 암호화된 문자열(데이터, 토큰만료 시간 포함)
    public String generateToken(JwtMember jwtMember, long tokenValidityMilleSeconds){
        Date now = new Date();
        return Jwts.builder()
                // header
                .header().type(constJwt.getBearerFormat())
                .and()
                //payload
                .issuer(constJwt.getIssuer())
                .issuedAt(now) // 토큰 생성일
                .expiration(new Date(now.getTime() + tokenValidityMilleSeconds)) // 토큰 만료 일시
                .claim(constJwt.getClaimKey(), makeClaimMyUserToJson(jwtMember)) // JSON화된 객체 처리
                /// key(signedMember) , value(JwtMember객체 JSON으로 변환하여 담기)
                .signWith(secretKey)
                .compact();
    }
    public String makeClaimMyUserToJson(JwtMember jwtMember){
        return objectMapper.writeValueAsString(jwtMember);
    }

    public JwtMember getJwtMemberFromToken(String token){
        Claims claims = getClaims(token);

        //signedMember 키 값으로 담겨져 있는 value를 String타입으로 리턴
        String json = claims.get(constJwt.getClaimKey(), String.class);

        //JSON > Object, json문자열을 JwtMember 객체로 변환
        return objectMapper.readValue(json, JwtMember.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
