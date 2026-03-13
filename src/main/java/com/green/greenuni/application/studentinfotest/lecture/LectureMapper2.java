package com.green.greenuni.application.studentinfotest.lecture;

import com.green.greenuni.application.lectures.model.LectureDetailReq;
import com.green.greenuni.application.studentinfotest.lecture.model.LectureStudentInfoReq2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LectureMapper2 {
    List<LectureStudentInfoReq2> studentInfo(@Param("req") LectureDetailReq req,
                                             @Param("loginUserId") Long loginUserId);
}
