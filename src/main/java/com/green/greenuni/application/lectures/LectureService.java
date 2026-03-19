package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.*;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor

public class LectureService {
    private final LectureMapper lectureMapper;

    @Transactional
    public int postLecture(LectureCreateReq req){
        validateLecture(req, null); // 유효성 검사 공통화
        lectureMapper.createLecture(req);
        return lectureMapper.createSchedule(req);
    }

    public ResultResponse editLeceture(Long lectureId, LectureCreateReq req){
        validateLecture(req,lectureId);
        lectureMapper.updateSchedule(lectureId, req);
        lectureMapper.editLeceture(lectureId, req);
        return new ResultResponse<>("강의가 수정되었습니다.", null);
    }

    // 공통 검증 로직
    private void validateLecture(LectureCreateReq req,Long lectureId) {
        Map<String, Object> conflictMap = lectureMapper.checkScheduleConflict(
                req.getRoomNumber(), req.getDayOfWeek(),
                req.getStartPeriod(), req.getEndPeriod(),
                req.getYear(), req.getSemester(), lectureId, req.getLoginUserId(), req.getMaxStd()
        );

        if (Integer.parseInt(String.valueOf(conflictMap.get("roomConflict"))) > 0) {
            throw new IllegalStateException("해당 강의실에 같은 시간대 강의가 이미 존재합니다.");
        }
        if (Integer.parseInt(String.valueOf(conflictMap.get("professorConflict"))) > 0) {
            throw new IllegalStateException("해당 시간에 이미 다른 수업이 있습니다.");
        }
        if (Integer.parseInt(String.valueOf(conflictMap.get("capacityOver"))) > 0) {
            throw new IllegalStateException("강의실 최대 수용 인원을 초과했습니다.");
        }
    }

    public ResultResponse<String> getProName(Long loginUserId) {
        String name = lectureMapper.getProName(loginUserId);
        return new ResultResponse<>("교수명 조회 성공", name);
    }

    // 건물 목록 조회
    public ResultResponse<?> getBuildings() {
        List<String> list = lectureMapper.getBuildings();
        return new ResultResponse<>("건물 목록 조회 성공", list);
    }

    // 특정 건물의 호실 목록 조회
    public ResultResponse<?> getRoomsByBuilding(String building) {
        List<LectureRoom> list = lectureMapper.getRoomsByBuilding(building);
        return new ResultResponse<>("강의실 목록 조회 성공", list);
    }

    public LectureEditRes findByIdForEdit(Long lectureId) {
        LectureEditRes res = lectureMapper.findByIdForEdit(lectureId);

        // 전체 건물 목록
        res.setBuildingList(lectureMapper.getBuildings());
        // 해당 강의 건물의 강의실 목록
        if (res.getBuilding() != null) {
            res.setRoomList(lectureMapper.getRoomsByBuilding(res.getBuilding()));
        }
        return res;
    }

    public List<MyLectureListRes> getMyLectureList(MyLectureListReq req, Long loginUserId, String role){
        if ("student".equalsIgnoreCase(role)) {
            return lectureMapper.getMyCourseList(loginUserId); // 학생: 수강신청 목록
        } // professor, admin 둘 다 getMyLectureList로 → XML에서 role로 분기
        return lectureMapper.getMyLectureList(loginUserId, role);
    }

    public List<LectureListRes> getLectureList(LectureListReq req){
        return lectureMapper.getLectureList(req);
    }

    public LectureDetailRes getOneLectures(LectureDetailReq req){
        return lectureMapper.findById(req);
    }

    public List<LectureStudentInfoReq> getStudentInfo(LectureDetailReq req, Long loginUserId, String role) {

        if ("admin".equalsIgnoreCase(role)) {
            return lectureMapper.studentInfoForAdmin(req,loginUserId);  // 소유자 체크 없는 쿼리
        }
        return lectureMapper.studentInfo(req, loginUserId);
    }

    @Transactional
    public void updateStatus(Long lectureId, String status) {
        lectureMapper.updateLectureStatus(lectureId, status);
    }
}
