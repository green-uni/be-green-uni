package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureDetailReq {
    private Long loginUserId;
    private Long memberId;
    private Long lectureId;
    private int page;
    private int size;
    private int startIdx;

}
