package com.green.greenuni.application.attendance;

import com.green.greenuni.application.student.AttendListRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    void setAttendList(Long lectureId, String attendDate);
    List<AttendListRes> getStudentAttendList(Long lectureId, String attendDate);
}
