package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileRes {
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
    private Integer stdMajorId;
    private String stdMajorName;
    private String stdStatus;
    private int totalCredit;

    // 교수 정보
    private String degree;
    private String position;
    private String labRoom;
    private String labTel;
    private Integer profMajorId;
    private String profMajorName;
    private String profStatus;
    private int totalLecture;

    // 스태프 정보
    private String stfStatus;
}
