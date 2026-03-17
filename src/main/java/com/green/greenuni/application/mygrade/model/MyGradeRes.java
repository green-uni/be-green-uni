package com.green.greenuni.application.mygrade.model;

import lombok.Data;

@Data
public class MyGradeRes {
    private String year; //강의년도
    private int semester; //학기
    private String lectureType; //구분(전공, 교양)
    private String lectureName; //강의명
    private int credit; //학점
    private String gradeLetter; //최종 등급
    private String finalScore; //평점
}
