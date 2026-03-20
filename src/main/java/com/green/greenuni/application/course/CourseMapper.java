package com.green.greenuni.application.course;

import com.green.greenuni.application.course.model.*;
import com.green.greenuni.application.lectures.model.LectureDetailRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseMapper {
    List<CourseListRes> getCourseList();
    // 총 학점 조회
    int getTotalEnrolledCredits(@Param("memberId") long memberId);
    // 수강 목록 조회
    List<MyCourseListRes> getMyCourseList(@Param("memberId") long memberId);
    // 수강 취소
    int deleteCourse(CourseDelReq req);
    // 수강 신청
    int saveCourse(CoursePostReq req);

    LectureDetailRes getLectureDetail(long lectureId);
    CourseStudentReq getStudentDetail(long memberId);

    void increaseRemStd(long lectureId);
    void decreaseRemStd(long lectureId);

    Map<String, Object> findMaxPage(CourseListMaxPageReq req);

    Long getCourseId(@Param("lectureId") long lectureId, @Param("memberId") long memberId);
    List<Long> getEnrolledStudentIds(@Param("lectureId") long lectureId);
}
