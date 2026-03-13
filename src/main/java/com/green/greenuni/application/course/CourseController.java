package com.green.greenuni.application.course;

import com.green.greenuni.application.course.model.CourseDelReq;
import com.green.greenuni.application.course.model.CourseListRes;
import com.green.greenuni.application.course.model.MyCourseResponseDto;
import com.green.greenuni.configuration.model.ResultResponse;
import com.green.greenuni.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResultResponse<?> getCourseList() {
        List<CourseListRes> list = courseService.getCourseList();
        return new ResultResponse<>("수강신청 리스트", list);
    }

    @GetMapping("/my")
    public ResultResponse<MyCourseResponseDto> getMyCourse(@AuthenticationPrincipal UserPrincipal userPrincipal){
        MyCourseResponseDto result = courseService.getMyCourseData(userPrincipal.getLoginUserId());
        return new ResultResponse<>("내 수강 내역", result);
    }

    @DeleteMapping
    public ResultResponse<?> deleteCourse(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @ModelAttribute CourseDelReq req) {
        req.setMemberId(userPrincipal.getLoginUserId());
        log.info("req: {}", req);
        int result = courseService.deleteCourse(req);
        return new ResultResponse<>("수강 취소", result);
    }
}
