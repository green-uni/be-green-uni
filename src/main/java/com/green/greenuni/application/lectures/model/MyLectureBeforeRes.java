package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class MyLectureBeforeRes{
    private String status;
    private String lectureType;
    private String lectureName;
    private String proName;
    private int credit;
    private String day_of_week;
    private String start_period;
    private String end_period;
    private String building;
    private String roomNumber;
    private int academicYear;
    private int maxStd;
    private String is_open;

}
