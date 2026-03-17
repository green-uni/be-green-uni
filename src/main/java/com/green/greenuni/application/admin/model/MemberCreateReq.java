package com.green.greenuni.application.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberCreateReq {
    private String role;
    private String birth;
    private String name;
    private String email;
    private String tel;
    private String emergencyTel;
    private String address;
    private String detailAddress;
    private String postcode;

    private String entryDate;
    private String exitDate;
    private String pic;
    private String status;

    private Integer majorId;

    private Integer academicYear;
    private Integer semester;

    private String degree;
    private String position;
    private String labRoom;
    private String labTel;

    private Long memberId;
    private String code;
    private String password;
}
