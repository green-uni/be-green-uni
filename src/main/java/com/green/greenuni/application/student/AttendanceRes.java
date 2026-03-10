package com.green.greenuni.application.student;

import lombok.Data;

@Data
public class AttendanceRes {
    private String attendDate; //출결일
    private long studentCode; //학번
    private String studentName; //이름
    private int academicYear; //학년
    private String majorName; //학과
    private String status; //출결상태
    private String reason; //사유
}
