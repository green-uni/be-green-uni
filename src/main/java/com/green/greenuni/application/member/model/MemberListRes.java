package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListRes {
    private String code;
    private String name;
    private String entryDate;
    private String exitDate;
    private String email;
    private String tel;

    private String stdMajorName;
    private String profMajorName;

    private String stdStatus;
    private String profStatus;
    private String stfStatus;
}
