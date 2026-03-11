package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class MyLectureBeforeReq {
    private int memberId;
    private int page;
    private int size;
    private int startIdx;

}
