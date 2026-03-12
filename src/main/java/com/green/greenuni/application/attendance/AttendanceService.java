package com.green.greenuni.application.attendance;

import com.green.greenuni.application.student.AttendListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper attendanceMapper;

    @Transactional
    public List<AttendListRes> getAttendList(Long lectureId, String attendData) {
        int count = attendanceMapper.checkAttendList(lectureId, attendData);

        if (count == 0) {
            attendanceMapper.setAttendList(lectureId, attendData);
        }
        return attendanceMapper.getStudentAttendList(lectureId, attendData);
    }

}
