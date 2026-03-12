package com.green.greenuni.application.student.course;

import com.green.greenuni.application.student.course.model.CourseListRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<CourseListRes> getCourseList();
}
