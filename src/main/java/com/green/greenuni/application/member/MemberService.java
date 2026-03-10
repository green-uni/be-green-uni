package com.green.greenuni.application.member;

import com.green.greenuni.application.member.model.MemberCreateReq;
import com.green.greenuni.application.member.model.MemberCreateRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    MemberCreateRes res = new MemberCreateRes();

    public MemberCreateRes createMember(MemberCreateReq req){
        // member table insert
        memberMapper.createMember(req);

        // 멤버코드: 입학연도(4자리) + 구분코드(1자리) + 순번(3자리)

        // 입학연도
        String entryYear = req.getEntryDate().substring(0,4);

        // role에 따른 구분코드
        String roleNum;
        switch (req.getRole()){
            case "admin"     -> roleNum = "3";
            case "professor" -> roleNum = "2";
            default          -> roleNum = "1";
        }

        // 멤버코드 생성
        String code = entryYear + roleNum + String.format("%03d", req.getMemberId());
        req.setCode(code);

        // 생일을 초기 비밀번호
        String rawPw = req.getBirth().replace("-", ""); //- 제거
        String hashedPw = passwordEncoder.encode(rawPw);
        req.setPassword(hashedPw);

        // 멤버코드와 비밀번호 삽입
        memberMapper.updateMemberCodeAPw(req);

        switch (req.getRole()){
            case "admin"     -> memberMapper.createStaff(req);
            case "professor" -> memberMapper.createProfessor(req);
            default          -> memberMapper.createStudent(req);
        }

        res.setMemberCode(req.getCode());
        res.setMemberRole(req.getRole());
        return res;
    }
}