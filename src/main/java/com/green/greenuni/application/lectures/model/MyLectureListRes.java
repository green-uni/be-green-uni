package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class MyLectureListRes {
    private String status;
    private Long memberId;
    private Long loginUserId;
    private Long lectureId;
    private String lectureType;
    private String lectureName;
    private String proName;
    private Integer credit;
    private String dayOfWeek;
    private Integer startPeriod;
    private Integer endPeriod;
    private String building;
    private String roomNumber;
    private Integer academicYear;
    private Integer maxStd;
    private String is_open;

}
