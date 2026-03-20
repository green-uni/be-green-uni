package com.green.greenuni.application.member;

import com.green.greenuni.application.admin.model.*;
import com.green.greenuni.application.member.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    int createMember(MemberCreateReq req);
    int createStudent(MemberCreateReq req);
    int createProfessor(MemberCreateReq req);
    int createStaff(MemberCreateReq req);
    int updateMemberCodeAPw(MemberCreateReq req);

    // 관리자 멤버 목록 조회 및 수정
    List<MemberListRes> findAllMember(MemberListReq req);
    Map<String, Object> findMaxPage(MemberListMaxPageReq req);
    int modStdStatus(MemberEditListReq req);
    int modProfStatus(MemberEditListReq req);
    int modStfStatus(MemberEditListReq req);

    // login
    MemberFindByCodeRes findByCode(String code);

    // 프로파일 화면
    MemberProfileRes findUserProfile( long id );

    // 멤퍼 프로파일 수정
    int modMemberBySelf(MemberEditReq req);
    int modProfessorMySelf(MemberEditReq req);
    int updateMemberPic(MemberCreateReq req);

    // 비밀번호 변경 (로그인유저 직접)
    MemberFindByIdRes findById(long loginUserId);
    int changePw(MemberPwChangeReq req);

    // 비밀번호 변경 (메일 인증 후)
    int resetPw(MemberPwResetReq req);
}
