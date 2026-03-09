package com.green.greenuni.application.member.model;

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
    private String entryDate;
    private String exitDate;
    private String pic;
    private String status;

    private int majorId;

    private int academicYear;
    private int semester;

    private String degree;
    private String position;
    private String labRoom;
    private String labTel;
}
