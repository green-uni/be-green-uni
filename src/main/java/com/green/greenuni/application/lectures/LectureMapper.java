package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.LectureCreateReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureMapper {
    int createLecture(LectureCreateReq req);
}
