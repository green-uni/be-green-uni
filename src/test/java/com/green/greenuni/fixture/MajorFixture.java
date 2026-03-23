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

    private static final List<String> ROOM_POOL = List.of(
            "공학관 301호", "공학관 302호", "공학관 401호",
            "경영관 201호", "경영관 202호", "경영관 203호",
            "인문관 101호", "인문관 102호",
            "미래관 501호", "미래관 502호"
    );

    private static final List<String> TEL_POOL = List.of(
            "0212345678",   // 02 → 9자리
            "0223456789",   // 02 → 9자리
            "0534561234",   // 053 → 10자리
            "0544561234",   // 054 → 10자리
            "0554561234",   // 055 → 10자리
            "0514561234",   // 051 → 10자리
            "0524561234",   // 052 → 10자리
            "0314561234",   // 031 → 10자리
            "0324561234",   // 032 → 10자리
            "0334561234"    // 033 → 10자리
    );

    public List<Long> insertMajors() {
        List<Object[]> majors = List.of(
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

        for (int i = 0; i < majors.size(); i++) {
            Object[] m = majors.get(i);

            MajorCreateReq req = new MajorCreateReq();
            req.setName((String) m[0]);
            req.setCollege((String) m[1]);
            req.setActive((String) m[2]);
            req.setRoom(ROOM_POOL.get(i));   // ← 추가
            req.setTel(TEL_POOL.get(i));     // ← 추가
            req.setStartDate(LocalDate.of(2000, 3, 1));
            req.setCapacity(40);

            majorMapper.createMajor(req);

            if ("running".equals(m[2])) {
                activeMajorIds.add(req.getMajorId());
            }
        }

        System.out.println("✅ 전공 생성 - 전체: " + majors.size()
                + " / 정상: " + activeMajorIds.size() + " / 폐지: 2");
        return activeMajorIds;
    }

    /**
     * 교수 생성 후 학과장 배정 (majorId 1개당 professorId 1명 랜덤 배정)
     * - majorIds 전체(폐지 포함 10개)에 배정하려면 majorMapper.getAllMajorIds() 활용 가능
     */
    public void updateChairProfessors(List<Long> majorIds, List<Long> professorIds) {
        List<Long> shuffled = new ArrayList<>(professorIds);
        Collections.shuffle(shuffled);

        for (int i = 0; i < majorIds.size(); i++) {
            long chairId = shuffled.get(i % shuffled.size());
            majorMapper.updateChairProfessor(majorIds.get(i), chairId); // ← Mapper 메서드 필요
        }

        System.out.println("✅ 학과장 배정 완료 - " + majorIds.size() + "개 전공");
    }
}