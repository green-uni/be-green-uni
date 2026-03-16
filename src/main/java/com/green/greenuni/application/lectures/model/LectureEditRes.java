package com.green.greenuni.application.lectures.model;


import lombok.Data;

import java.util.List;

@Data
    public class LectureEditRes {
    // 강의 기본정보 (LectureDetailRes 필드 그대로)
    private Long lectureId;
    private Long loginUserId;
    private String status;
    private Long majorId;
    private String majorName;
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
        // 드롭다운용
        private List<String> buildingList;
        private List<LectureRoom> roomList;
    }

