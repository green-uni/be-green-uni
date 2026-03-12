package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.application.lectures.model.LectureRoom;
import com.green.greenuni.application.lectures.model.MyLectureBeforeReq;
import com.green.greenuni.application.lectures.model.MyLectureBeforeRes;
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
}
