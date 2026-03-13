package com.green.greenuni.application.course.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MyCourseResponseDto {
    private long memberId;
    private int totalEnrolledCredits;
    private List<MyCourseListRes> courses;
}
