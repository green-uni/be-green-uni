package com.green.greenuni.configuration.security;

import com.green.greenuni.configuration.constants.ConstJwt;
import com.green.greenuni.configuration.util.MyCookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
