package com.green.greenuni.application.lectures;


import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor

public class LectureController {
    private final LectureService lectureService;

    @PostMapping("/create")
    public ResultResponse<?> postLecture(@RequestBody LectureCreateReq req){
        int result=lectureService.postLecture(req);
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
}
