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

        //course 수강생 전체와 비교해서 새로추가된 학생까지 출력
        List<AttendListRes> courseList = attendanceMapper.getCourseStudentList(lectureId, attendDate);

        // attendance에 없는 학생 찾아서 추가
        for (AttendListRes course : courseList) {
            boolean exists = attendList.stream().anyMatch(a -> a.getCode().equals(course.getCode()));
            if (!exists) {
                attendList.add(course);
            }
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

    //교수가 출석한 기록이 있으면 초록색으로 표시하려고 사용
    public List<String> getRecordedDates(Long lectureId) {
        return attendanceMapper.getRecordedDates(lectureId);
    }
}
