package com.green.greenuni.application.major;

import com.green.greenuni.application.course.model.CourseListMaxPageReq;
import com.green.greenuni.application.course.model.CourseStudentReq;
import com.green.greenuni.application.lectures.model.LectureDetailRes;
import com.green.greenuni.application.major.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        // 등록 시에는 majorId가 없으므로 null 전달
        validateMajorDuplicate(req.getName(), req.getRoom(), req.getTel(), req.getChairProfessorId(), null);
        return majorMapper.createMajor(req);
    }

    @Transactional
    public int modifyMajor(MajorModReq req) {
        // 수정 시에는 본인 ID를 전달하여 본인 데이터는 중복 검사에서 제외
        validateMajorDuplicate(req.getName(), req.getRoom(), req.getTel(), req.getChairProfessorId(), req.getMajorId());
        return majorMapper.modifyMajor(req);
    }

    /* 학과 정보 중복 검증 공통 로직 */
    private void validateMajorDuplicate(String name, String room, String tel, Long chairProfessorId, Long majorId) {
        // 1. 학과명 중복 체크 (MajorMapper의 XML 쿼리에서 majorId가 다를 때만 체크하도록 수정 필요)
        if (majorMapper.checkMajorNameDuplicate(name, majorId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 학과명입니다.");
        }

        // 2. 학과 사무실 중복 체크
        if (majorMapper.checkMajorOfficeDuplicate(room, majorId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 학과 사무실입니다.");
        }

        // 3. 전화번호 중복 체크
        if (majorMapper.checkMajorPhoneDuplicate(tel, majorId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 전화번호입니다.");
        }

        // 4. 학과장 중복 체크
        if (chairProfessorId != null && majorMapper.checkMajorProfessorDuplicate(chairProfessorId, majorId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 다른 학과의 학과장으로 임명된 교수입니다.");
        }
    }

    public MajorDetailRes getMajor(long majorId) {
        return majorMapper.getMajor(majorId);
    }

    public List<ProfessorListDto> getProfessorList(){
        return majorMapper.getProfessorList();
    }

    public Map<String, Object> getMajorMaxPage(MajorListMaxPageReq req){ return majorMapper.findMaxPage(req); }
}
