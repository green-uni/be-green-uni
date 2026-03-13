package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.*;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor

public class LectureService {
    private final LectureMapper lectureMapper;

    @Transactional
    public int postLecture(LectureCreateReq req){
        lectureMapper.createLecture(req);       // lecture INSERT (lectureId 자동 세팅)
        return lectureMapper.createSchedule(req);
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

    public List<MyLectureListRes> getMyLectureList(MyLectureListReq req, Long loginUserId, String role){
        if ("student".equalsIgnoreCase(role)) {
            return lectureMapper.getMyCourseList(loginUserId); // 학생: 수강신청 목록
        } else if ("professor".equalsIgnoreCase(role)) {
            return lectureMapper.getMyLectureList(loginUserId);    // 교수: 본인 강의만
        }
        return lectureMapper.getMyLectureList(null);               // 관리자: 전체
    }

    public List<LectureListRes> getLectureList(LectureListReq req){
        return lectureMapper.getLectureList(req);
    }

    public LectureDetailRes getOneLectures(LectureDetailReq req){
        return lectureMapper.findById(req);
    }

    public List<LectureStudentInfoReq> getStudentInfo(LectureDetailReq req, Long loginUserId) {
        return lectureMapper.studentInfo(req, loginUserId);
    }

    public void updateStatus(Long lectureId, String status) {
        lectureMapper.updateLectureStatus(lectureId, status);
    }
}
