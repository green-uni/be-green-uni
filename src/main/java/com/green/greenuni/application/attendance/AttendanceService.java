package com.green.greenuni.application.attendance;

import com.green.greenuni.application.attendance.model.AttendListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper attendanceMapper;

    @Transactional
    public List<AttendListRes> getAttendList(Long lectureId, String attendDate) {
        attendanceMapper.setAttendList(lectureId, attendDate);
        return attendanceMapper.getStudentAttendList(lectureId, attendDate);
    }
}
