package com.green.greenuni.application.lectures;


import com.green.greenuni.application.lectures.model.*;
import com.green.greenuni.configuration.model.ResultResponse;

import com.green.greenuni.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor


public class LectureController {
    private final LectureService lectureService;

    @PostMapping("/create")
    public ResultResponse<?> postLecture(@AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody LectureCreateReq req){

        if (userPrincipal == null) {
            return new ResultResponse<>("로그인 정보가 만료되었습니다. 다시 로그인해주세요.", 0);
        }

        req.setLoginUserId(userPrincipal.getLoginUserId());
        int result=lectureService.postLecture(req);
        System.out.println("전달받은 데이터: " + req.toString());
        return new ResultResponse<>("강의개설이 되었습니다.", result);
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

    @GetMapping("/me/before")
    public ResultResponse<?> meBefore(@AuthenticationPrincipal UserPrincipal userPrincipal,
            @ModelAttribute MyLectureBeforeReq req) {
        List<MyLectureBeforeRes> result = lectureService.meBefore(req);
        System.out.println(">>> 강의 승인 전 목록 조회 요청이 들어왔습니다!");
        return new ResultResponse<>("성공", result);
    }

    @GetMapping
    public ResultResponse<?> getLectureList(@AuthenticationPrincipal UserPrincipal userPrincipal,
            @ModelAttribute LectureListReq req){
        List<LectureListRes> result = lectureService.getLectureList(req);
        System.out.println("전체강의 목록 조회 요청이 들어왔습니다!");
        return new ResultResponse<>("성공", result);
    }

    @GetMapping("/{lectureId}")
    public ResultResponse<?> getAllLectures(@ModelAttribute LectureDetailReq req){
        List<LectureDetailRes> result=lectureService.getAllLectures(req);
        return new ResultResponse<>("강의목록상세보기", result);
    }

}
