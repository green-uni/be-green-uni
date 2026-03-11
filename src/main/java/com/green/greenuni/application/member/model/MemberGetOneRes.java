package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberGetOneRes {
    private long memberId;
    private String code;
    private String password;
    private String name;
}
