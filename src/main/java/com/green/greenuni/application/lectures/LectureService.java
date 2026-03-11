package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.application.lectures.model.LectureRoom;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class LectureService {
    private final LectureMapper lectureMapper;

    public int postLecture(LectureCreateReq req){
         return lectureMapper.createLecture(req);
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

}
