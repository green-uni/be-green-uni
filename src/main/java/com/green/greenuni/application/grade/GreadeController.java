package com.green.greenuni.application.grade;

import com.green.greenuni.application.grade.model.GradeUpdateReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class GreadeController {
    public final GradeService gradeService;

    //성적 조회
    @GetMapping("/{lectureId}/grades")
    public ResultResponse<?> getGradeList(@PathVariable Long lectureId) {
        return new ResultResponse<>("성적 조회 성공", gradeService.getGradeList(lectureId));
    }

    //성적 수정
    @PatchMapping("/{lectureId}/grades")
    public ResultResponse<?> updateGrades(@PathVariable Long letureId,
                                          @RequestBody List<GradeUpdateReq> req) {
        gradeService.updateGrades(req);
        return new ResultResponse<>("성적 저장 성공", null);
    }
}