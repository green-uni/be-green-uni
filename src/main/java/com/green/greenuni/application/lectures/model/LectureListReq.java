package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureListReq {
    private String loginUserId;
    private int page;
    private int size;
    private int startIdx;
}
