package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.MajorListRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {
    List<MajorListRes> findMajorAll();
}
