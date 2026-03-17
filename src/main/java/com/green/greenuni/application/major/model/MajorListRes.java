package com.green.greenuni.application.major.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MajorListRes {
    private String name;
    private String room;
    private String tel;
    private String chairProfessorName;
    private int capacity;
    private long majorId;
    private int professorCount;
    private String college;
    private String active;
}
