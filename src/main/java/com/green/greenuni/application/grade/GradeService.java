package com.green.greenuni.application.grade;

import com.green.greenuni.application.grade.model.GradeRes;
import com.green.greenuni.application.grade.model.GradeUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeMapper gradeMapper;

    public List<GradeRes> getGradeList(Long lectureId) {
        return gradeMapper.getGradeList(lectureId);
    }

    public void updateGrades(List<GradeUpdateReq> req) {
        for (GradeUpdateReq updateList : req) {
            gradeMapper.updateGrade(updateList);
        }
    }
}
