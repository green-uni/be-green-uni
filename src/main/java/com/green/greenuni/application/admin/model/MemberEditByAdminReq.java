package com.green.greenuni.application.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberEditByAdminReq {
    private String name;
    private String role;

    private String exitDate;
    private String status;

    // 학생 정보
    private Integer academicYear;
    private Integer semester;
    private String stdStatus;

    // 교수 정보
    private String degree;
    private String position;
    private Integer profMajorId;
    private String profMajorName;
    private String profStatus;

    // 스태프 정보
    private String stfStatus;

}
