package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureDetailReq {
    private String loginUserId;
    private long memberId;
    private long lectureId;
    private int page;
    private int size;
    private int startIdx;

}
