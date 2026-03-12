package com.green.greenuni.application.attendance;

import com.green.greenuni.application.student.AttendListReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    List<AttendListReq> getStudentAttendList(Long lectureId, String attendData);
}
