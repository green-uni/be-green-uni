package com.green.greenuni.application.attendance.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class AttendSetReq {
    private Long lectureId;
    private Long courseId;
    private Long memberId;
    private LocalDate attendDate;
    private String status;
}