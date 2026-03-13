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

    MemberFindByCodeRes findByCode(String code);

    MemberProfileRes findUserProfile( long id );
}
