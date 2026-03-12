package com.green.greenuni.application.student.course;

import com.green.greenuni.application.student.course.model.CourseListRes;
import com.green.greenuni.application.student.course.model.MyCourseListReq;
import com.green.greenuni.application.student.course.model.MyCourseResponseDto;
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

//    @GetMapping("/{memberId}")
//    public ResultResponse<MyCourseResponseDto> getMyCourse(@PathVariable long memberId){
//        MyCourseResponseDto dto = courseService.getMyCourseData(memberId);
//        return new ResultResponse<>("내 수강 조회", dto);
//    }

    @GetMapping("/{memberId}")
    public ResultResponse<MyCourseResponseDto> getMyCourse(@AuthenticationPrincipal UserPrincipal userPrincipal){
        long memberId = userPrincipal.getLoginUserId();
        log.info("조회 요청 memberId: {}", memberId);
        MyCourseResponseDto result = courseService.getMyCourseData(memberId);
        return new ResultResponse<>("success", result);
    }
}
