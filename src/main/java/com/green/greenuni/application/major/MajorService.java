package com.green.greenuni.application.major;

import com.green.greenuni.application.course.model.CourseListMaxPageReq;
import com.green.greenuni.application.course.model.CourseStudentReq;
import com.green.greenuni.application.lectures.model.LectureDetailRes;
import com.green.greenuni.application.major.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MajorService {
    private final MajorMapper majorMapper;

    public List<MajorListForCreateRes> listForCreate(){ return majorMapper.listForCreate(); }

    public List<MajorListRes> getMajorList(MajorListMaxPageReq req){
        return majorMapper.getMajorList(req);
    }

    @Transactional
    public int createMajor(MajorCreateReq req) {
        // 1. 학과명 중복 체크
        int nameConflict = majorMapper.checkMajorNameDuplicate(req.getName());
        if (nameConflict > 0) {
            throw new RuntimeException("이미 존재하는 학과명입니다.");
        }

        // 2. 학과 사무실 중복 체크
        int officeConflict = majorMapper.checkMajorOfficeDuplicate(req.getRoom());
        if (officeConflict > 0) {
            throw new RuntimeException("이미 사용 중인 학과 사무실입니다.");
        }

        // 3. 전화번호 중복 체크
        int phoneConflict = majorMapper.checkMajorPhoneDuplicate(req.getTel());
        if (phoneConflict > 0) {
            throw new RuntimeException("이미 사용 중인 전화번호입니다.");
        }

        // 4. 학과장 중복 체크
        int professorConflict = majorMapper.checkMajorProfessorDuplicate(req.getChairProfessorId());
        if(professorConflict > 0){
            throw new RuntimeException("이미 임명된 학과장입니다.");
        }

        // 모든 조건 통과 시 학과 등록 진행
        return majorMapper.createMajor(req);
    }

    public int modifyMajor(MajorModReq req){
        return majorMapper.modifyMajor(req);
    }

    public MajorDetailRes getMajor(long majorId) {
        return majorMapper.getMajor(majorId);
    }

    public List<ProfessorListDto> getProfessorList(){
        return majorMapper.getProfessorList();
    }

    public Map<String, Object> getMajorMaxPage(MajorListMaxPageReq req){ return majorMapper.findMaxPage(req); }
}
