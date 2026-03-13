package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LectureDetailRes {
    private long memberId;
    private String lectureName;
    private String year;
    private int semester;
    private String lectureType;
    private long lectureId;
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