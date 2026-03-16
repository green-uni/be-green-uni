package com.green.greenuni.application.admin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListRes {
    private long memberId;
    private String code;
    private String name;
    private String role;

    private String stdMajorName;
    private String profMajorName;

    private String academicYear;
    private String semester;

    private String position;

    private String stdStatus;
    private String profStatus;
    private String stfStatus;

    private String entryDate;
    private String exitDate;

    private String tel;
    private String email;
}
