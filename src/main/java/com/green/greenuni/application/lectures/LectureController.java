package com.green.greenuni.application.lectures;


import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.application.lectures.model.MyLectureBeforeReq;
import com.green.greenuni.application.lectures.model.MyLectureBeforeRes;
import com.green.greenuni.configuration.model.ResultResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor


public class LectureController {
    private final LectureService lectureService;

    @PostMapping("/create")
    public ResultResponse<?> postLecture(@RequestBody LectureCreateReq req){
        int result=lectureService.postLecture(req);
        System.out.println("전달받은 데이터: " + req.toString());
        return new ResultResponse<>("강의개설이 되었습니다.", result);
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
    public ResultResponse<?> meBefore(@ModelAttribute MyLectureBeforeReq req) {
        List<MyLectureBeforeRes> result = lectureService.meBefore(req);
        System.out.println(">>> 강의 승인 전 목록 조회 요청이 들어왔습니다!");
        return new ResultResponse<>("성공", result);
    }
}
