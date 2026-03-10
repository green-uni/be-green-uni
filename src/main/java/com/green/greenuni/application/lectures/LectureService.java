package com.green.greenuni.application.lectures;

import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class LectureService {
    private final LectureMapper lectureMapper;

    public int postLecture(LectureCreateReq req){
         return lectureMapper.createLecture(req);
    }

}
