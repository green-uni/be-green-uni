package com.green.greenuni.application.course.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyCourseListRes {
    private long lectureId;
    private String majorName;
    private String lectureName;
    private String building;
    private String roomNumber;
    private String lectureType;
    private int academicYear;
    private String proName;
    private String dayOfWeek;
    private int credit;
    private int maxStd;
    private int remStd;
    private String status;
}
