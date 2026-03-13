package com.green.greenuni.application.member;

import com.green.greenuni.application.member.model.MemberLoginReq;
import com.green.greenuni.application.member.model.MemberLoginRes;
import com.green.greenuni.application.member.model.MemberProfileReq;
import com.green.greenuni.application.member.model.MemberProfileRes;
import com.green.greenuni.configuration.model.JwtMember;
import com.green.greenuni.configuration.model.ResultResponse;
import com.green.greenuni.configuration.model.UserPrincipal;
import com.green.greenuni.configuration.security.JwtTokenManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/login")
    public ResultResponse<?> logIn(HttpServletResponse res, @RequestBody MemberLoginReq req){
        log.info("login req :{}", req);
        MemberLoginRes memberLoginRes = memberService.logIn(req);

        // 보안 쿠키 처리
        if(memberLoginRes != null){ // 로그인 성공시
            JwtMember jwtMember = new JwtMember( memberLoginRes.getLoginUserId(),  memberLoginRes.getRole() ); // 로그인 유저 정보를 담을 객체 생성
            jwtTokenManager.issue( res, jwtMember ); // 로그인 유저에게 토큰 생성 후 쿠키를 담아 응답으로 발행
        }

        return new ResultResponse<>("로그인 성공", memberLoginRes);
    }

    @PostMapping("/logout")
    public ResultResponse<?> logOut(HttpServletResponse res){
        jwtTokenManager.logOut(res);
        return new ResultResponse<>("로그아웃 성공", 1);
    }

    @GetMapping("/me")
    public ResultResponse<?> getLoginUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal
                                            , @RequestParam long loginUserId){
        MemberProfileReq req = new MemberProfileReq( loginUserId, userPrincipal.getLoginUserRole() );
        log.info("req: {}", req);
        MemberProfileRes res = null;
        return new ResultResponse<>("프로파일 유저정보", req);
    }

}
