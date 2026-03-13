package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LectureMeDetailReq {
    private String loginUserId;
    private long memberId;
    private String lectureId;
    private String type;
    private int credit;
    private int maxStd;
    private int remStd;
    private String building;
    private String roomNumber;
    private String dayOfWeek;
    private int startPeriod;
    private int endPeriod;


}
