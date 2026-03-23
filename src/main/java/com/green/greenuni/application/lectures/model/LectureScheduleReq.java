package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureScheduleReq {
    private Long lectureId;
    private Integer roomId;
    private String dayOfWeek;
    private Integer startPeriod;
    private Integer endPeriod;
}