package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProfileProfessorRes {
    // 교수 정보
    private String degree;
    private String position;
    private String labRoom;
    private String labTel;
    private Integer profMajorId;
    private String profMajorName;
    private String profStatus;
}
