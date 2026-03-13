package com.green.greenuni.application.course;

import com.green.greenuni.application.course.model.CourseDelReq;
import com.green.greenuni.application.course.model.CourseListRes;
import com.green.greenuni.application.course.model.CoursePostReq;
import com.green.greenuni.application.course.model.MyCourseListRes;
import com.green.greenuni.application.lectures.model.LectureDetailRes;
import com.green.greenuni.application.student.StudentInfoReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<CourseListRes> getCourseList();
    // 총 학점 조회
    int getTotalEnrolledCredits(@Param("memberId") long memberId);
    // 수강 목록 조회
    List<MyCourseListRes> getMyCourseList(@Param("memberId") long memberId);
    // 수강 취소
    void deleteAttendance(CourseDelReq req);
    int deleteCourse(CourseDelReq req);
    // 수강 취소 후 잔여 인원 증가
    int plusLectureRemainder(@Param("lectureId") long lectureId);
    // 수강 신청
    int saveCourse(CoursePostReq req);

    LectureDetailRes getLectureDetail(long lectureId);
    StudentInfoReq getStudentDetail(long memberId);
}
