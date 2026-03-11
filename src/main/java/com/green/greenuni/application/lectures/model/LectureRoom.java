package com.green.greenuni.application.lectures.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureRoom {
    private int roomId;
    private String building;
    private String roomNumber;
    private int capacity;
}
