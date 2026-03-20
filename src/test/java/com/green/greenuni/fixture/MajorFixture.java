package com.green.greenuni.fixture;

import com.green.greenuni.application.major.MajorMapper;
import com.green.greenuni.application.major.model.MajorCreateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class MajorFixture {

    @Autowired
    private MajorMapper majorMapper;

    /**
     * 전공 10개 생성
     * - 정상 8개 / 폐지 2개
     * - 단과대학 3개 분산 (공과대/경영대/인문대)
     *
     * @return 정상 전공 majorId 목록만 반환 (강의·학생 배정용)
     */
    public List<Long> insertMajors() {
        List<Object[]> majors = List.of(
                // { name, college, active }
                new Object[]{"컴퓨터공학과",   "공과대학", "running"},
                new Object[]{"소프트웨어학과", "공과대학", "running"},
                new Object[]{"전기전자공학과", "공과대학", "running"},
                new Object[]{"경영학과",       "경영대학", "running"},
                new Object[]{"마케팅학과",     "경영대학", "running"},
                new Object[]{"회계학과",       "경영대학", "running"},
                new Object[]{"영어영문학과",   "인문대학", "running"},
                new Object[]{"심리학과",       "인문대학", "running"},
                new Object[]{"멀티미디어학과", "공과대학", "closed"},
                new Object[]{"e-비즈니스학과", "경영대학", "closed"}
        );

        List<Long> activeMajorIds = new ArrayList<>();

        for (Object[] m : majors) {
            MajorCreateReq req = new MajorCreateReq();
            req.setName((String) m[0]);
            req.setCollege((String) m[1]);
            req.setActive((String) m[2]);
            req.setStartDate(LocalDate.of(2000, 3, 1));
            req.setCapacity(40);

            majorMapper.createMajor(req); // useGeneratedKeys → majorId 세팅

            if ("running".equals(m[2])) {
                activeMajorIds.add(req.getMajorId());
            }
        }

        System.out.println("✅ 전공 생성 - 전체: " + majors.size()
                + " / 정상: " + activeMajorIds.size() + " / 폐지: 2");
        return activeMajorIds;
    }
}

