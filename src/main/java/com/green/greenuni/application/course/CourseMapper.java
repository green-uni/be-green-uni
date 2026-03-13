package com.green.greenuni.application.course;

import com.green.greenuni.application.course.model.*;
import com.green.greenuni.application.lectures.model.LectureDetailRes;
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
    CourseStudentReq getStudentDetail(long memberId);

    // 수강 신청 후 잔여 인원 감소
    void minusLectureRemainder(long lectureId);
}
