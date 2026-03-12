package com.green.greenuni.configuration.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class JwtMember {
    private long loginUserId;
    private String loginUserRole;
}
