package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureCreateReq {
    private int proId;
    private String proName;
    private String year;
    private int semester;
    private String lectureName;
    private int targetGrade;
    private int maxStd;
    private String lectureType;
    private String majorName;
    private int credit;
    private String day_of_week;
    private String start_period;
    private String end_period;
    private String roomNumber;
    private String refBooks;
    private String goal;
    private String weeklyPlan;

    // 강의실 선택을 위한 필드
//    private int room_id;
//    private String building;
//    private String room_Number;
//
}