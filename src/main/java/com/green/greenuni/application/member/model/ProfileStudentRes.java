package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProfileStudentRes {
    // 학생 정보
    private Integer academicYear;
    private Integer semester;
    private Integer stdMajorId;
    private String stdMajorName;
    private String stdStatus;
    private int stdTotalCredit;
}
