package com.green.greenuni.application.student;

import lombok.Data;

@Data
public class StudentInfoReq {
    private long studentCode; //학번
    private String majorName; //학과
    private String studentName; //성명
    private int academicYear; //학년
    private String gradeLetter; //점수(등급)
}
