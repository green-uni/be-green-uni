package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureDto {
    private Long lectureId;
    private int year;
    private int semester;
}