package com.green.greenuni.application.course.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseStudentReq {
    private long majorId;
    private long memberId;
    private int academicYear;
}
