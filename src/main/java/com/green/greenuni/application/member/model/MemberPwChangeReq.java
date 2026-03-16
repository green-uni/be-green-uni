package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberPwChangeReq {
    private String oldPassword;
    private String newPassword;
    private Long loginUserId;
}
