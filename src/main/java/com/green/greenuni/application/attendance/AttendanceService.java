package com.green.greenuni.application.attendance;

import com.green.greenuni.application.attendance.model.AttendListRes;
import com.green.greenuni.application.attendance.model.AttendUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper attendanceMapper;

    @Transactional
    public List<AttendListRes> getAttendList(String lectureId, String attendDate) {
        List<AttendListRes> attendList = attendanceMapper.getStudentAttendList(lectureId, attendDate);

        // attendance 데이터 없으면 course에서 수강생 목록 가져오기
        if (attendList.isEmpty()) {
            return attendanceMapper.getCourseStudentList(lectureId, attendDate);
        }
        return attendList;
    }

    //저장버튼 클릭 시 INSERT + UPDATE까지 같이 실행
    public void updateAttendList(String lectureId, List<AttendUpdateReq> reqList) {
        attendanceMapper.setAttendList(lectureId, reqList.get(0).getAttendDate() ); //INSERT부분
        for (AttendUpdateReq req : reqList) {
            attendanceMapper.updateAttend(req); //update부분
        }
    }
}
