package com.green.greenuni.application.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberEditListReq {
    private long memberId;
    private String status;
    private String role;
}