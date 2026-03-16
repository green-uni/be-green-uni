package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberFindByIdRes {
    private long memberId;
    private String password;
}
