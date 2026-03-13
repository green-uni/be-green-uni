package com.green.greenuni.application.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberProfileReq {
    private long loginUserId;
    private String loginUserRole;
}