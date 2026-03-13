package com.green.greenuni.application.attendance;

import com.green.greenuni.application.attendance.model.AttendListRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    void setAttendList(@Param("lectureId") String lectureId, @Param("attendDate") String attendDate);
    List<AttendListRes> getStudentAttendList(@Param("lectureId") String lectureId, @Param("attendDate") String attendDate);
}
