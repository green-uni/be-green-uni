package com.green.greenuni.configuration.security;

import com.green.greenuni.configuration.constants.ConstJwt;
import com.green.greenuni.configuration.model.JwtMember;
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
//    private final ObjectMapper objectMapper;
//    private final ConstJwt constJwt;
//    private final SecretKey secretKey;
//
//    public JwtTokenProvider(ObjectMapper objectMapper, ConstJwt constJwt) {
//        this.objectMapper = objectMapper;
//        this.constJwt = constJwt;
//        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(constJwt.getSecretKey()));
//
//        log.info("constJwt: {}", this.constJwt);
//    }
//
//    public String generateAccessToken(JwtMember jwtMember){ // 토큰 만료 15분
//        return generateToken(jwtMember , constJwt.getAccessTokenValidityMilliseconds());
//    }
//    public String generateRefreshToken(JwtMember jwtMember){ // 토큰 만료 15일
//        return generateToken(jwtMember , constJwt.getRefreshTokenValidityMilliseconds());
//    }
//
//    public String generateToken(JwtMember jwtMember, long tokenValidityMilleSeconds){
//        Date now = new Date();
//        return Jwts.builder()
//                // header
//                .header().type(constJwt.getBearerFormat())
//                .and()
//                //payload
//                .issuer(constJwt.getIssuer())
//                .issuedAt(now) // 토큰 생성일
//                .expiration(new Date(now.getTime() + tokenValidityMilleSeconds)) // 토큰 만료 일시
//                .claim(constJwt.getClaimKey(), makeClaimMyUserToJson(jwtMember)) // JSON화된 객체 처리
//                /// ////// key(signedMember) , value(JwtMember객체 JSON으로 변환하여 담기)
//                .signWith(secretKey)
//                .compact();
//    }
//    public String makeClaimMyUserToJson(JwtMember jwtMember){
//        return objectMapper.writeValueAsString(jwtMember);
//    }

}
