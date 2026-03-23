package com.green.greenuni.application.attendance;

import com.green.greenuni.application.attendance.model.AttendListRes;
import com.green.greenuni.application.attendance.model.AttendSetReq;
import com.green.greenuni.application.attendance.model.AttendUpdateReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    // 기존 메서드 유지하고 더미용 추가
    void setAttendList(@Param("lectureId") String lectureId,
                       @Param("attendDate") String attendDate); // 기존 유지
    void setAttendListByReq(AttendSetReq req); // 더미용 추가
    List<AttendListRes> getStudentAttendList(@Param("lectureId") String lectureId, @Param("attendDate") String attendDate);
    List<AttendListRes> getCourseStudentList(@Param("lectureId") String lectureId, @Param("attendDate") String attendDate);
    void updateAttend(AttendUpdateReq req);
    List<String> getRecordedDates(@Param("lectureId") Long lectureId);
}
