package com.green.greenuni.application.major.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MajorListMaxPageReq {
    private int size;
    private String majorName;
    private String collegeName;
    private String active;
}