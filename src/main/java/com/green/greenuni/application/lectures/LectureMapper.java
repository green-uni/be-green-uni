package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.*;
import com.green.greenuni.configuration.model.ResultResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LectureMapper {
    int createLecture(LectureCreateReq req);
    int createSchedule(LectureCreateReq req);

    String getProName(@Param("loginUserId") Long loginUserId);
    List<String> getBuildings();
    List<LectureRoom> getRoomsByBuilding(String building);
    //중복체크
    Map<String, Object> checkScheduleConflict(@Param("roomNumber") String roomNumber,
                                              @Param("dayOfWeek") String dayOfWeek,
                                              @Param("startPeriod") Integer startPeriod,
                                              @Param("endPeriod") Integer endPeriod,
                                              @Param("year") Integer year,
                                              @Param("semester") Integer semester,
                                              @Param("lectureId") Long lectureId,
                                              @Param("loginUserId") Long loginUserId,
                                              @Param("maxStd") Integer maxStd);

    LectureEditRes findByIdForEdit(@Param("lectureId") Long lectureId);
    int editLeceture(@Param("lectureId") Long lectureId, @Param("req") LectureCreateReq req);
    int updateSchedule(@Param("lectureId") Long lectureId, @Param("req") LectureCreateReq req);


    List<MyLectureListRes> getMyLectureList(@Param("loginUserId") Long loginUserId,@Param("role") String role);
    List<MyLectureListRes> getMyCourseList(@Param("loginUserId") Long loginUserId);

    List<LectureListRes> getLectureList(LectureListReq req);


    LectureDetailRes findById(LectureDetailReq req);
    List<LectureStudentInfoReq> studentInfo(@Param("req") LectureDetailReq req,
                                             @Param("loginUserId") Long loginUserId,
                                             @Param("role") String role);
    List<LectureStudentInfoReq> studentInfoForAdmin(@Param("req") LectureDetailReq req,
                                                    @Param("loginUserId") Long loginUserId);


    void updateLectureStatus(@Param("lectureId") Long lectureId, @Param("status") String status);

    String getProfStatus(Long loginUserId);
    void deleteSchedule(LectureDetailReq req);
    void deleteLecture(LectureDetailReq req);
    int countStudentsByLectureId(LectureDetailReq req);
}
