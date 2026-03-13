package com.green.greenuni.application.course.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CoursePostReq {
    private long courseId;
    private long lectureId;
    private long memberId;
}
