package com.green.greenuni.application.mygrade;

import com.green.greenuni.configuration.model.ResultResponse;
import com.green.greenuni.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class MyGradesController {
    private final MyGradesService myGradesService;

    @GetMapping("/mygrades")
    public ResultResponse<?> getMyGrades(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long loginUserId = userPrincipal.getLoginUserId();
        return new ResultResponse<>("내 성적 조회 성공", myGradesService.getMyGrades(loginUserId));
    }
}
