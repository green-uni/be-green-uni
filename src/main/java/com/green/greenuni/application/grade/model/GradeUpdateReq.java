package com.green.greenuni.application.grade.model;

import lombok.Data;

@Data
public class GradeUpdateReq {

    //성적 수정용
    private Long courseId;
    private int midScore;
    private int finScore;
    private int assignmentScore;
    private int attendScore;
}
