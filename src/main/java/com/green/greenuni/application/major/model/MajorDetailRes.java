package com.green.greenuni.application.major.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MajorDetailRes {
    private long majorId;
    private String name;
    private String active;
    private String college;
    private String room;
    private String tel;
    private String chairProfessor;
    private Integer capacity;
    private LocalDate startDate;
    private String info;
}