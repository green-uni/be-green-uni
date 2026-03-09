package com.green.greenuni.application.member;

import com.green.greenuni.application.member.model.MemberCreateReq;
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

    private int studentCode;
    private int professorCode;
    private int adminCode;

    public int createMember(MemberCreateReq req){
        // req에서 받아온 입학연도와 role로 로그인 아이디 만들기
        String memberRole;
        String memberEntryYear = req.getEntryDate().substring(0,4);

        // role 값이 무엇인지에 따라
        String role = req.getRole();
        if(role.equals("student")){
            memberRole = "1";
        }else if(role.equals("professor")){
            memberRole = "2";
        }else if(role.equals("admin")){
            memberRole = "3";
        }



        // req에서 받아온 생일을 비밀번호 만들기
        String hashedPw = passwordEncoder.encode(req.getBirth());
        log.info("hashedPw:{}", hashedPw);


        return 0;
    }
}