package com.green.greenuni.application.lectures;


import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
