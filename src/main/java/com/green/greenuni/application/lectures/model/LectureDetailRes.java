package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LectureDetailRes {
    private long memberId;
    private long loginUserId;
    private String status;
    private long majorId;
    private String lectureName;
    private String year;
    private int semester;
    private String lectureType;
    private long lectureId;
    private Integer credit;
    private Integer maxStd;
    private Integer remStd;
    private String building;
    private String roomNumber;
    private String dayOfWeek;
    private String startPeriod;
    private String endPeriod;
    private Integer academicYear;

    private String refBooks;
    private String goal;
    private String weeklyPlan;
}