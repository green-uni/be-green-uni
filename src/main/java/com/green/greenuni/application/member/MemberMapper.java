package com.green.greenuni.application.member;

import com.green.greenuni.application.member.model.MemberCreateReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    int createMember(MemberCreateReq req);
    int createStudent(MemberCreateReq req);
    int createProfessor(MemberCreateReq req);
    int createStaff(MemberCreateReq req);
}
