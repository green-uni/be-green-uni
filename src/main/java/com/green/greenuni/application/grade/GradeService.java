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

            //각 점수 0~100 범위 체크
            if(updateList.getMidScore() < 0 || updateList.getMidScore() >100 ||
                updateList.getFinScore() < 0 || updateList.getFinScore() > 100 ||
                updateList.getAssignmentScore() < 0 || updateList.getAssignmentScore() > 100 ||
                updateList.getAttendScore() < 0 || updateList.getAttendScore() > 100) {
                throw new IllegalArgumentException("등록 가능한 점수를 초과하였습니다.");
            }

            //총점 100점 초과 체크
            int total = updateList.getMidScore() + updateList.getFinScore()
                + updateList.getAssignmentScore() + updateList.getAttendScore();
            if (total > 100) {
                throw new IllegalArgumentException("총점이 100점을 초과할 수 없습니다.");
            }

            gradeMapper.updateGrade(updateList);
        }
    }
}
