package com.green.greenuni.application.mygrade;

import com.green.greenuni.application.mygrade.model.MyGradeRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyGradesMapper {
    List<MyGradeRes> getMyGrades(@Param("loginUserId") Long loginUserId);
}
