package com.green.greenuni.application.member.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginRes {
    private long loginUserId;
    private String name;
    private String code;
}
