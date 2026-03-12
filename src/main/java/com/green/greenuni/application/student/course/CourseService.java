package com.green.greenuni.application.student.course;

import com.green.greenuni.application.student.course.model.CourseListRes;
import com.green.greenuni.application.student.course.model.MyCourseListRes;
import com.green.greenuni.application.student.course.model.MyCourseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
