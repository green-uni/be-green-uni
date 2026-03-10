package com.green.greenuni.application.lectures.model;

import com.green.greenuni.application.student.StudentInfoRes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Year;
import java.util.List;

@Getter
@Setter
@ToString
public class LectureDetailRes {
    private Year year; //연도
    private int semester; //학기
    private String lectureName; //강의명
    private String professorName; //교수명
    private String lectureType; //이수구분
    private String majorName; //전공명
    private int academicYear; //대상학년
    private int credit; //이수학점
    private String building; //강의건물
    private String roomNumber; //강의호실
    private int currentStudent; //현재 수강하는 인원
    private String dayOfWeek; //강의요일
    private String startPeriod; //강의시작시간
    private String endPeriod; //강의종료시간
    private List<StudentInfoRes> students; //수강학생정보(필요시 꺼내쓸용도. 지우지말기ㅠ)
}