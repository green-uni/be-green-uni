package com.green.greenuni.application.lectures.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LectureRoom {
    private Long roomId;
    private String roomNumber;
    private String building;
    private int capacity;
}
