package com.green.greenuni.application.attendance;

import com.green.greenuni.application.student.AttendListRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    int checkAttendList(Long lectureId, String attendData);
    void setAttendList(Long lectureId, String attendData);
    List<AttendListRes> getStudentAttendList(Long lectureId, String attendData);
}
