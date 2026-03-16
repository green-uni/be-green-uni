package com.green.greenuni.application.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberListMaxPageReq {
    private int size;
    private String role;
    private String memberName;
    private String majorName;

}
