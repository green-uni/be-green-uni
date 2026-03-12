package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureListRes {
    private String lectureId;
    private String lectureType;
    private String lectureName;
    private String majorName;
    private String proName;
    private Integer credit;
    private String day_of_week;
    private String start_period;
    private String end_period;
    private String building;
    private String roomNumber;
    private Integer academicYear;
    private Integer year;
    private Integer semester;

}
