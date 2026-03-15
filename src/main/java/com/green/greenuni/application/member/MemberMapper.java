package com.green.greenuni.application.member;

import com.green.greenuni.application.member.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    int createMember(MemberCreateReq req);
    int createStudent(MemberCreateReq req);
    int createProfessor(MemberCreateReq req);
    int createStaff(MemberCreateReq req);
    int updateMemberCodeAPw(MemberCreateReq req);

    List<MemberListRes> findAllMember(MemberListReq req);
    int findMaxPage(MemberListMaxPageReq req);
    int modStdStatus(MemberModifyListReq req);
    int modProfStatus(MemberModifyListReq req);
    int modStfStatus(MemberModifyListReq req);

    // login
    MemberFindByCodeRes findByCode(String code);

    // 프로파일 화면
    MemberProfileRes findUserProfile( long id );

    int modMemberBySelf(MemberModifyReq req);
    int modProfessorMySelf(MemberModifyReq req);
}
