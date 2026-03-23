package com.green.greenuni.application.course;

import com.green.greenuni.application.admin.model.MemberListMaxPageReq;
import com.green.greenuni.application.course.model.*;
import com.green.greenuni.application.lectures.model.LectureDetailRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
        int result = courseMapper.deleteCourse(req);
        // 삭제 성공 시 lecture 테이블의 rem_std 컬럼 1 증가
        if (result > 0) {
            courseMapper.increaseRemStd(req.getLectureId());
        }
        return result;
    }

    @Transactional
    public long postCourse(CoursePostReq req) {
        // 1. 강의 정보 조회 (정원, 대상학년, 개설전공 확인용)
        LectureDetailRes lecture = courseMapper.getLectureDetail(req.getLectureId());
        // 2. 학생 정보 조회 (학생의 학년, 전공 확인용)
        CourseStudentReq student = courseMapper.getStudentDetail(req.getMemberId());
        // [체크 1] 정원 초과 여부
        if (lecture.getRemStd() <= 0) {
            throw new RuntimeException("수강 정원이 초과되었습니다.");
        }
        // [체크 2] 학년 일치 여부
        if (lecture.getAcademicYear() != student.getAcademicYear()) {
            throw new RuntimeException("해당 강의는 " + lecture.getAcademicYear() + "학년 대상 강의입니다.");
        }
        // [체크 3] 전공 일치 여부
        if ("전공".equals(lecture.getLectureType()) && lecture.getMajorId() != student.getMajorId()) {
            throw new RuntimeException("해당 전공 학생만 신청 가능한 과목입니다.");
        }
        // [체크 4] 중복된 수업 시간일 경우 제한

        // 모든 조건 통과 시 수강 신청 진행
        int result = courseMapper.saveCourse(req);

        // lecture 테이블의 rem_std 컬럼 1 감소
        if (result > 0) {
            courseMapper.decreaseRemStd(req.getLectureId());
        }

        return result;
    }

    public Map<String, Object> getCourseMaxPage(CourseListMaxPageReq req){ return courseMapper.findMaxPage(req); }

    /**
     * 특정 강의를 수강 중인 모든 학생의 member_id 리스트 조회
     */
    public List<Long> getEnrolledStudentIds(long lectureId) {
        return courseMapper.getEnrolledStudentIds(lectureId);
    }

    /**
     * 특정 강의와 특정 학생의 매핑 정보(course_id) 조회
     */
    public Long getCourseId(long lectureId, long memberId) {
        return courseMapper.getCourseId(lectureId, memberId);
    }
}
