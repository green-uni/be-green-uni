package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureCreateReq {
    private Long loginUserId;
    private String code;
    private String loginUserName;
    private String isOpen;
    private Integer majorId;
    private String majorName;
    private Integer year;
    private Integer semester;
    private String lectureName;
    private Integer credit;
    private String lectureType;
    private String refBooks;
    private String goal;
    private String weeklyPlan;
    private Integer academicYear;
    private Integer maxStd;
    private String startDate;
    private String endDate;
    private String roomNumber;
    private String dayOfWeek;
    private Integer startPeriod;
    private Integer endPeriod;
    private Long lectureId;

}