package com.green.greenuni.application.attendance;

import com.green.greenuni.application.student.AttendListRes;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor

public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/{lectureId}")
    public ResultResponse<?> getAttendance(
        @PathVariable Long lectureId,
        @RequestParam( value = "attendDate", required = false ) String attendDate) {
        log.info("강의 ID: {}, 조회 날짜: {}", lectureId, attendDate);
        List<AttendListRes> attendList = attendanceService.getAttendList(lectureId, attendDate);
        return new ResultResponse<>("출석부 조회 성공", attendList);
    }
}
