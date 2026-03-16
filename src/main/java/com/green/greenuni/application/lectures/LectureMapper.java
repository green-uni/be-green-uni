package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LectureMapper {
    int createLecture(LectureCreateReq req);
    int createSchedule(LectureCreateReq req);

    String getProName(@Param("loginUserId") Long loginUserId);
    List<String> getBuildings();
    List<LectureRoom> getRoomsByBuilding(String building);

    int editLeceture(LectureCreateReq req);
    int updateSchedule(LectureCreateReq req);

    List<MyLectureListRes> getMyLectureList(@Param("loginUserId") Long loginUserId);
    List<MyLectureListRes> getMyCourseList(@Param("loginUserId") Long loginUserId);

    List<LectureListRes> getLectureList(LectureListReq req);


    LectureDetailRes findById(LectureDetailReq req);
    List<LectureStudentInfoReq> studentInfo(@Param("req") LectureDetailReq req,
                                             @Param("loginUserId") Long loginUserId);

    void updateLectureStatus(@Param("lectureId") Long lectureId, @Param("status") String status);

}
