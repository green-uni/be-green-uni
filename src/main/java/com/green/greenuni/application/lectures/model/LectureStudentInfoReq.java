package com.green.greenuni.application.lectures.model;

import lombok.Data;

@Data
public class LectureStudentInfoReq {
    private String majorName; //학과
    private String studentCode; //학번
    private String studentName; //성명
    private int academicYear; //학년
    private String gradeLetter; //점수(등급)
    private Long lectureId; //강의ID
    private int attendCount; //출석 횟수
    private int lateCount; //지각 횟수
    private int absentCount; //결석 횟수
}
