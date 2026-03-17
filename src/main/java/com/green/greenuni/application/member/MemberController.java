package com.green.greenuni.application.member;

import com.green.greenuni.application.member.model.*;
import com.green.greenuni.configuration.model.JwtMember;
import com.green.greenuni.configuration.model.ResultResponse;
import com.green.greenuni.configuration.model.UserPrincipal;
import com.green.greenuni.configuration.security.JwtTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/member")
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

    @PostMapping("/reissue")
    public ResultResponse<?> reissue(HttpServletResponse res, HttpServletRequest req){
        jwtTokenManager.reissue(req, res);
        return new ResultResponse<>("AT 재발행", null);
    }

    @GetMapping("/me")
    public ResultResponse<?> findLoginUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal){
        long id = userPrincipal.getLoginUserId(); // 현재 로그인한 user Id
        String role = userPrincipal.getLoginUserRole(); // 현재 로그인한 user role
        log.info("loginUserId: {}",id);
        MemberProfileRes res = memberService.findLoginUserProfile( id , role );
        return new ResultResponse<>("프로파일 유저정보", res);
    }

    @PutMapping("/me/mod")
    public ResultResponse<?> modMemberBySelf(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestPart MemberEditReq req,
                                             @RequestPart(required = false) MultipartFile pic){
        long id = userPrincipal.getLoginUserId(); // 현재 로그인한 user Id
        String role = userPrincipal.getLoginUserRole(); // 현재 로그인한 user role

        log.info("loginUserId: {}",id);
        String res = memberService.modMemberBySelf( id , role, req, pic );

        return new ResultResponse<>("로그인 유저 정보 수정", res);
    }

    @PatchMapping("/me/pw")
    public ResultResponse<?> changePwInProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @RequestBody MemberPwChangeReq req){
        long loginUserId = userPrincipal.getLoginUserId(); // 현재 로그인한 user Id
        log.info("loginUserId: {}", loginUserId);
        int result = memberService.changePw(loginUserId, req);
        return new ResultResponse<>("비밀번호 변경 성공", result);
    }

    @PatchMapping("/pw")
    public ResultResponse<?> ResetPw(@RequestBody @Valid MemberPwResetReq req){
        log.info("memberId: {}", req.getMemberId());
        int result = memberService.ResetPw(req);
        return new ResultResponse<>("비빌번호 변경 성공", result);
    }
}
