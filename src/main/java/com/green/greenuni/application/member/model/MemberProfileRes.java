package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberProfileRes {
    private long memberId;
    private String name;
    private String code;
    private String role;
    private String birth;
    private String email;
    private String tel;
    private String emergencyTel;
    private String address;
    private String detailAddress;
    private int postcode;
    private String entryDate;
    private String exitDate;
    private String pic;

    // 학생 정보
    private Integer academicYear;
    private Integer semester;
    private String stdMajorName;
    private String stdStatus;

    // 교수 정보
    private String degree;
    private String position;
    private String labRoom;
    private String labTel;
    private String profMajorName;
    private String profStatus;

    // 스태프 정보
    private String stfStatus;
}
