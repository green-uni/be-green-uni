package com.green.greenuni.application.course;

import com.green.greenuni.application.course.model.CourseDelReq;
import com.green.greenuni.application.course.model.CourseListRes;
import com.green.greenuni.application.course.model.MyCourseListRes;
import com.green.greenuni.application.course.model.MyCourseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseMapper courseMapper;

    public List<CourseListRes> getCourseList(){
        return courseMapper.getCourseList();
    }

    public MyCourseResponseDto getMyCourseData(long memberId) {
        int totalCredits = courseMapper.getTotalEnrolledCredits(memberId);
        List<MyCourseListRes> courseList = courseMapper.getMyCourseList(memberId);

        MyCourseResponseDto response = new MyCourseResponseDto();
        response.setMemberId(memberId);
        response.setTotalEnrolledCredits(totalCredits);
        response.setCourses(courseList);

        return response;
    }

    @Transactional // 삭제와 인원 복구를 하나의 작업으로 묶기 위해
    public int deleteCourse(CourseDelReq req) {
        // 1. 자식 테이블(출석) 데이터 삭제
        courseMapper.deleteAttendance(req);
        // 2. 부모 테이블(수강) 데이터 삭제
        int result = courseMapper.deleteCourse(req);
        // 3. 인원 수 복구 (+1)
        if (result > 0) {
            courseMapper.plusLectureRemainder(req.getLectureId());
        }
        return result;
    }
}
