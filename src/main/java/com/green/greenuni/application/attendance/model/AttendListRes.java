package com.green.greenuni.application.attendance.model;

import lombok.Data;

@Data
public class AttendListRes {
    private Long attendId; //출결id
    private String attendDate; //출결일
    private String code; //학번
    private String name; //이름
    private int academicYear; //학년
    private String major; //학과
    private String status; //출결상태
    private String reason; //사유
    private String lectureId; //강의id를 불러옵니다
}
