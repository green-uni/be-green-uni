package com.green.greenuni.application.attendance;

import com.green.greenuni.application.student.AttendListReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper attendanceMapper;

    public List<AttendListReq> getAttendList(Long lectureId, String attendData) {
        return attendanceMapper.getStudentAttendList(lectureId, attendData);
    }

}
