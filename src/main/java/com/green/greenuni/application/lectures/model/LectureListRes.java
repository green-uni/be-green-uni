package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureListRes {
    private Long loginUserId;
    private String status;
    private Long lectureId;
    private String lectureType;
    private String lectureName;
    private String majorName;
    private String proName;
    private Integer credit;
    private String dayOfWeek;
    private String startPeriod;
    private String endPeriod;
    private String building;
    private String roomNumber;
    private Integer academicYear;
    private Integer year;
    private Integer semester;



}
