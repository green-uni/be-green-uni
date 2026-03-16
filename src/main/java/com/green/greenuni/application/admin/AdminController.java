package com.green.greenuni.application.admin;

import com.green.greenuni.application.admin.model.MemberEditByAdminReq;
import com.green.greenuni.application.admin.model.MemberEditByAdminRes;
import com.green.greenuni.application.member.MemberService;
import com.green.greenuni.application.member.model.*;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin')") // admin 권한만 API 사용 가능
public class AdminController {
    private final MemberService memberService;

    // 계정 생성
    @PostMapping("/members")
    public ResultResponse<?> createMember(@RequestPart MemberCreateReq req
            , @RequestPart(required = false) MultipartFile pic){
        log.info("createMember req: {}", req);
        MemberCreateRes res = memberService.createMember(req, pic);
        return new ResultResponse("계정생성", res);
    }

    // 전체 계정 조회
    @GetMapping("/members")
    public ResultResponse<?> findAllMember(@ModelAttribute MemberListReq req){
        List<MemberListRes> list = memberService.findAllMember(req);
        return new ResultResponse("전체 계정 조회", list);
    }

    // 전체 계정의 목록의 최대 페이지 계산
    @GetMapping("/members/max_page")
    public ResultResponse<?> getMemberMaxPage(@ModelAttribute MemberListMaxPageReq req){
        log.info("req: {}", req);
        int maxPage = memberService.getMemberMaxPage(req);
        return new ResultResponse<>("전체 계정 조회시 최대 페이지", maxPage);
    }

    // 전체 목록에서 계정 상태만 수정
    @PutMapping("/members/mod")
    public ResultResponse<?> modMembersStatus(@RequestBody List<MemberEditListReq> reqs){
        log.info("reqs: {}", reqs);
        memberService.modStatusList(reqs);
        return new ResultResponse<>("목록에서 계정 상태 수정", 1);
    }

//    @GetMapping("/members/{memberId}")
//    public ResultResponse<?> modMemberByAdmin(@RequestBody MemberEditByAdminReq req){
//        log.info("req: {}", req);
//        int res = memberService.editMemberByAdmin(req);
//        return new ResultResponse<>("관리자 계정 수정 완료", res);
//    }
}
