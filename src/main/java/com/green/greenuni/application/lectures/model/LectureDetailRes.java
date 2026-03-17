package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LectureDetailRes {
    private Long memberId;
    private String proName;
    private Long loginUserId;
    private String status;
    private Long majorId;
    private String majorName;
    private Long lectureId;
    private String lectureName;
    private Integer year;
    private int semester;
    private String lectureType;
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