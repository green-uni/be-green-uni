package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MajorMapper {
    List<MajorListForCreateRes> listForCreate();
    int createMajor(MajorCreateReq req); //학과 등록
    List<MajorListRes> getMajorList(MajorListMaxPageReq req);
    int modifyMajor(MajorModReq req);
    MajorDetailRes getMajor(long majorId);
    List<ProfessorListDto> getProfessorList();
    Map<String, Object> findMaxPage(MajorListMaxPageReq req);
    //더미 데이터 생성한다고 추가
    void updateChairProfessor(@Param("majorId") long majorId,
                              @Param("chairProfessorId") long chairProfessorId);

    //학과 등록 시 제한 추가
    int checkMajorNameDuplicate(@Param("name") String name, @Param("majorId") Long majorId);
    int checkMajorOfficeDuplicate(@Param("room") String room, @Param("majorId") Long majorId);
    int checkMajorPhoneDuplicate(@Param("tel") String tel, @Param("majorId") Long majorId);
    int checkMajorProfessorDuplicate(@Param("chairProfessorId") Long chairProfessorId, @Param("majorId") Long majorId);
}
