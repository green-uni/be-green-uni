package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.application.lectures.model.LectureRoom;
import com.green.greenuni.application.lectures.model.MyLectureBeforeReq;
import com.green.greenuni.application.lectures.model.MyLectureBeforeRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LectureMapper {
    int createLecture(LectureCreateReq req);
    int createSchedule(LectureCreateReq req);

    List<String> getBuildings();
    List<LectureRoom> getRoomsByBuilding(String building);

    int meBefore(MyLectureBeforeReq req);
}
