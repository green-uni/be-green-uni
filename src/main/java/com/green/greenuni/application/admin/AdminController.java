package com.green.greenuni.application.admin;

import com.green.greenuni.application.member.MemberService;
import com.green.greenuni.application.member.model.MemberCreateReq;
import com.green.greenuni.application.member.model.MemberCreateRes;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResultResponse createMember(@RequestPart MemberCreateReq req
            , @RequestPart(required = false) MultipartFile pic){
        log.info("createMember req: {}", req);
        MemberCreateRes result = memberService.createMember(req);
        return new ResultResponse("계정생성", result);
    }
}
