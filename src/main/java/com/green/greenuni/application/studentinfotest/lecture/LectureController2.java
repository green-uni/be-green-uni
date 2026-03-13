package com.green.greenuni.application.studentinfotest.lecture;

import com.green.greenuni.application.lectures.model.LectureDetailReq;
import com.green.greenuni.application.studentinfotest.lecture.model.LectureStudentInfoReq2;
import com.green.greenuni.configuration.model.ResultResponse;
import com.green.greenuni.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor

public class LectureController2 {
    private final LectureService2 lectureService2;

    @GetMapping("/{lectureId}/studentInfo")
    public ResultResponse<?> getStudentInfo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @ModelAttribute LectureDetailReq req) {
        Long loginUserId = userPrincipal.getLoginUserId();
        List<LectureStudentInfoReq2> result = lectureService2.getStudentInfo(req, loginUserId);
        return new ResultResponse<>("수강학생조회완료", result);
    }
}
