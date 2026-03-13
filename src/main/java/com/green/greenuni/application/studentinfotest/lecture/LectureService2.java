package com.green.greenuni.application.studentinfotest.lecture;

import com.green.greenuni.application.lectures.model.LectureDetailReq;
import com.green.greenuni.application.studentinfotest.lecture.model.LectureStudentInfoReq2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService2 {
    private final LectureMapper2 lectureMapper2;

    public List<LectureStudentInfoReq2> getStudentInfo(LectureDetailReq req, Long loginUserId) {
        return lectureMapper2.studentInfo(req, loginUserId);
    }
}
