package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {
    List<MajorListForCreateRes> listForCreate();
    int createMajor(MajorCreateReq req); //학과 등록
    List<MajorListRes> getMajorList();
    int modifyMajor(MajorModReq req);
    MajorDetailRes getMajor(long majorId);
}
