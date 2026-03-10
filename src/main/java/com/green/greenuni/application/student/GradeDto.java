package com.green.greenuni.application.student;

import lombok.Data;

@Data
public class GradeDto {
    private long studentCode; //학번
    private String studentName; //성명
    private int academicYear; //학년
    private int midScore; //중간평가
    private int finScore; //기말평가
    private int assignmentScore; //과제점수
    private int attendScore; //출석점수
    private int totalScore; //총점
    private String gradeLetter; //최종등급
}
