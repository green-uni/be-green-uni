package com.green.greenuni.application.course;

import com.green.greenuni.application.admin.model.MemberListMaxPageReq;
import com.green.greenuni.application.course.model.*;
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

    @PreAuthorize("hasRole('student')")
    @DeleteMapping
    public ResultResponse<?> deleteCourse(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @ModelAttribute CourseDelReq req) {
        req.setMemberId(userPrincipal.getLoginUserId());
        log.info("req: {}", req);
        int result = courseService.deleteCourse(req);
        return new ResultResponse<>("수강 취소", result);
    }

    @PreAuthorize("hasRole('student')")
    @PostMapping
    public ResultResponse<?> postCourse(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @RequestBody CoursePostReq req) {
        log.info("통신됐다!!");
        log.info("signedUserId: {}", userPrincipal.getLoginUserId());
        log.info("req: {}", req);
        req.setMemberId(userPrincipal.getLoginUserId());
        long id = courseService.postCourse(req);
        String message = id > 0 ? "success" : "failed";
        return new ResultResponse<>(message, id);
    }

    @GetMapping("/max_page")
    public ResultResponse<?> getCourseMaxPage(@ModelAttribute CourseListMaxPageReq req){
        log.info("req: {}", req);
        Map<String, Object> maxPage = courseService.getCourseMaxPage(req);
        return new ResultResponse<>("전체 계정 조회", maxPage);
    }
}
