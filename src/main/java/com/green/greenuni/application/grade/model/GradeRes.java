package com.green.greenuni.application.grade.model;

import lombok.Data;

@Data
public class GradeRes {

    //성적 조회용
    private Long courseId; //수강id
    private String code; //학번
    private String name; //성명
    private int academicYear; //학년
    private int midScore; //중간평가
    private int finScore; //기말평가
    private int assignmentScore; //과제점수
    private int attendScore; //출석점수
    private int totalScore; //총점
    private String gradeLetter; //최종등급
    private int lateCount; //지각 카운트
    private int absentCount; //결석 카운트
}
