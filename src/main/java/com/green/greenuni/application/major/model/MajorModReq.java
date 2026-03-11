package com.green.greenuni.application.major.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MajorModReq {
    private String name;
    private String active;
    private String room;
    private String tel;
    private String chairProfessor;
    private int capacity;
    private long majorId;
    private String college;
    private LocalDate startDate;
    private String info;
}
