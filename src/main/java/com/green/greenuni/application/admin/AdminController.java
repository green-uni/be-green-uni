package com.green.greenuni.application.admin;

import com.green.greenuni.application.member.MemberService;
import com.green.greenuni.application.member.model.MemberCreateReq;
import com.green.greenuni.application.member.model.MemberCreateRes;
import com.green.greenuni.application.member.model.MemberListReq;
import com.green.greenuni.application.member.model.MemberListRes;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        MemberCreateRes res = memberService.createMember(req, pic);
        return new ResultResponse("계정생성", res);
    }

    @GetMapping("/members")
    public ResultResponse findAllMember(@ModelAttribute MemberListReq req){
        List<MemberListRes> list = memberService.findAllMember(req);
        return new ResultResponse("전체 계정 조회", list);
    }
}
