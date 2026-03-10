package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.MajorCreateReq;
import com.green.greenuni.application.major.model.MajorCreateRes;
import com.green.greenuni.application.major.model.MajorListRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {
    List<MajorListRes> findMajorAll();
    int createMajor(MajorCreateReq req); //학과 등록
    List<MajorCreateRes> getMajorList();
}
