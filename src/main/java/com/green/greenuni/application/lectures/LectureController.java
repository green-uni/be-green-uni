package com.green.greenuni.application.lectures;


import com.green.greenuni.application.lectures.model.*;
import com.green.greenuni.configuration.model.ResultResponse;

import com.green.greenuni.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor


public class LectureController {
    private final LectureService lectureService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('professor')")
    public ResultResponse<?> postLecture(@AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody LectureCreateReq req){

        if (userPrincipal == null) {
            return new ResultResponse<>("로그인 정보가 만료되었습니다. 다시 로그인해주세요.", 0);
        }
        try {
            req.setLoginUserId(userPrincipal.getLoginUserId());
        int result=lectureService.postLecture(req);
        System.out.println("전달받은 데이터: " + req.toString());
        return new ResultResponse<>("강의개설이 되었습니다.", result);
        } catch (IllegalStateException e) {
            return new ResultResponse<>(e.getMessage(), 0); // 강의시 중복 메시지 반환
        }
    }

    @GetMapping("/edit/{lectureId}")
    @PreAuthorize("hasRole('professor')")
    public ResultResponse<?> editlecture(@PathVariable Long lectureId){
        LectureEditRes result = lectureService.findByIdForEdit(lectureId);
        return new ResultResponse<>("강의 수정 데이터 조회", result);
    }

    @PatchMapping("/edit/{lectureId}")
    @PreAuthorize("hasRole('professor')")
    public ResultResponse<?> editLecture(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @PathVariable Long lectureId,
                                         @RequestBody LectureCreateReq req) {
        req.setLoginUserId(userPrincipal.getLoginUserId());
        req.setLectureId(lectureId);
        return lectureService.editLeceture(lectureId, req);
    }

    @GetMapping("/professor")
    public ResultResponse<?> getProName(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return lectureService.getProName(userPrincipal.getLoginUserId());
    }

    @GetMapping("/buildings")
    public ResultResponse<?> getBuildings() {
        return lectureService.getBuildings();
    }

    @GetMapping("/Roomlist")
    public ResultResponse<?> getRooms(@RequestParam String building) {
        return lectureService.getRoomsByBuilding(building);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('professor', 'admin', 'student')")
    public ResultResponse<?> getMyLectureList(@AuthenticationPrincipal UserPrincipal userPrincipal,
            @ModelAttribute MyLectureListReq req) {
        Long loginUserId = userPrincipal.getLoginUserId();
        String role = userPrincipal.getLoginUserRole();
        List<MyLectureListRes> result = lectureService.getMyLectureList(req, loginUserId, role);
        System.out.println(">>> 강의 승인 전 목록 조회 요청이 들어왔습니다!");
        return new ResultResponse<>("성공", result);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('professor', 'admin', 'student')")
    public ResultResponse<?> getLectureList(@AuthenticationPrincipal UserPrincipal userPrincipal,
            @ModelAttribute LectureListReq req){
        List<LectureListRes> result = lectureService.getLectureList(req);
        System.out.println("전체강의 목록 조회 요청이 들어왔습니다!");
        return new ResultResponse<>("성공", result);
    }

    @GetMapping("/{lectureId}")
    @PreAuthorize("hasAnyRole('professor', 'admin', 'student')")
    public ResultResponse<?> getOneLectures(@ModelAttribute LectureDetailReq req){
        LectureDetailRes result=lectureService.getOneLectures(req);
        return new ResultResponse<>("강의목록상세보기", result);
    }

    @GetMapping("/{lectureId}/studentInfo")
    @PreAuthorize("hasAnyRole('professor', 'admin','student')")
    public ResultResponse<?> getStudentInfo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @ModelAttribute LectureDetailReq req) {
        Long loginUserId = userPrincipal.getLoginUserId();
        String role = userPrincipal.getLoginUserRole();
        List<LectureStudentInfoReq> result = lectureService.getStudentInfo(req, loginUserId, role);
        return new ResultResponse<>("수강학생조회완료", result);
    }

    @PatchMapping("/{lectureId}/statusedit")
    @PreAuthorize("hasRole('admin')")
    public ResultResponse<?> updateLectureStatus(
            @PathVariable Long lectureId,
            @RequestBody Map<String, String> body// { "status": "approved" or "rejected" }
    ) {
        lectureService.updateStatus(lectureId, body.get("status"));
        return new ResultResponse<>("강의 승인 상태 변경", null);
    }

}
