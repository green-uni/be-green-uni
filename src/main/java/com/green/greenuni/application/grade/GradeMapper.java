package com.green.greenuni.application.grade;

import com.green.greenuni.application.grade.model.GradeRes;
import com.green.greenuni.application.grade.model.GradeUpdateReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GradeMapper {
    List<GradeRes> getGradeList(@Param("lectureId") Long lectureId);
    void updateGrade(GradeUpdateReq req);
}
