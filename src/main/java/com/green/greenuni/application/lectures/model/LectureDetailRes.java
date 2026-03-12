package com.green.greenuni.application.lectures.model;

import com.green.greenuni.application.student.StudentInfoReq;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Year;
import java.util.List;

@Getter
@Setter
@ToString
public class LectureDetailRes {
    private long memberId;
    private String lectureName;
    private String year;
    private int semester;
    private String lectureType;
    private Integer credit;
    private Integer maxStd;
    private String building;
    private String roomNumber;
    private String day_of_week;
    private Integer academicYear;

    private String refBooks;
    private String goal;
    private String weeklyPlan;
}