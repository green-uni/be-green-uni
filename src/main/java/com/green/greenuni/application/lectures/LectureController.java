package com.green.greenuni.application.lectures;


import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.application.lectures.model.MyLectureBeforeReq;
import com.green.greenuni.configuration.model.ResultResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
    public ResultResponse<?> meBefore(@ModelAttribute MyLectureBeforeReq req){
        int result=lectureService.meBefore(req);
        return new ResultResponse<>("강의개설이 되었습니다.", result);
    }
}
