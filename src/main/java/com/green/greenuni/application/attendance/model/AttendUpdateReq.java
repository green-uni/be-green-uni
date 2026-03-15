package com.green.greenuni.application.attendance.model;

import lombok.Data;

@Data
public class AttendUpdateReq {
    private Long attendId; //출결id
    private String status; //출결상태
    private String reason; //사유
    private String attendDate; //출결일
}
