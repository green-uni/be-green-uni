package com.green.greenuni.application.attendance;

import com.green.greenuni.application.attendance.model.AttendListRes;
import com.green.greenuni.application.attendance.model.AttendUpdateReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor

public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/{lectureId}/attendance")
    public ResultResponse<?> getAttendance(
        @PathVariable String lectureId,
        @RequestParam( value = "attendDate", required = false ) String attendDate) {
        log.info("강의 ID: {}, 조회 날짜: {}", lectureId, attendDate);
        List<AttendListRes> attendList = attendanceService.getAttendList(lectureId, attendDate);
        return new ResultResponse<>("출석부 조회 성공", attendList);
    }

    @PatchMapping("/{lectureId}/attendance")
        public ResultResponse<?> updateAttendance(@PathVariable String lectureId,
                                                  @RequestBody List<AttendUpdateReq> reqList) {
            attendanceService.updateAttendList(lectureId, reqList);
            return new ResultResponse<>("출석 저장 성공", null);
    }

    @GetMapping("/{lectureId}/attendance/dates")
    public ResultResponse<?> getRecordedDates(@PathVariable Long lectureId) {
        return new ResultResponse<>("출석 날짜 조회 성공",
            attendanceService.getRecordedDates(lectureId));
    }
}
