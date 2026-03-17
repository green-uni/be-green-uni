package com.green.greenuni.application.mygrade;

import com.green.greenuni.application.mygrade.model.MyGradeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyGradesService {
    private final MyGradesMapper myGradesMapper;

    public List<MyGradeRes> getMyGrades(Long loginUserId) {
        return myGradesMapper.getMyGrades(loginUserId);
    }
}
