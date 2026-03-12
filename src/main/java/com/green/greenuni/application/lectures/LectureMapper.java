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

    List<MyLectureBeforeRes> meBefore(MyLectureBeforeReq req);

    List<LectureListRes> getLectureList(LectureListReq req);

    LectureDetailRes findById(LectureDetailReq req);
}
