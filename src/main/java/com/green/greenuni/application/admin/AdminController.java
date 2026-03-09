package com.green.greenuni.application.admin;

import com.green.greenuni.application.member.MemberService;
import com.green.greenuni.application.member.model.MemberCreateReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResultResponse createMember(@RequestBody MemberCreateReq req){
        log.info("create req: {}", req);
        int result = memberService.createMember(req);
        return new ResultResponse("계정생성", result);
    }
}
