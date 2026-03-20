package com.green.greenuni.application.course.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseListMaxPageReq {
    private int size;
    private String lectureType;
    private int academicYear;
    private String lectureName;
    private String majorName;
}