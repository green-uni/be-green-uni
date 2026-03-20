package com.green.greenuni.fixture;

import com.green.greenuni.application.lectures.LectureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;
import com.green.greenuni.application.lectures.model.LectureCreateReq;
import com.green.greenuni.application.lectures.model.LectureScheduleReq;
import java.time.LocalDate;
import java.util.*;

import java.util.Locale;

@Component
public class LectureFixture {

    @Autowired
    private LectureMapper lectureMapper;

    private final Faker faker = new Faker(new Locale("ko"));

    private final String[] LECTURE_NAMES = {
            "자료구조론", "알고리즘분석", "운영체제", "데이터베이스", "컴퓨터네트워크",
            "마케팅원론", "회계학개론", "경영전략", "인사관리론", "재무관리",
            "디지털회로", "신호처리", "제어공학", "전자기학", "반도체공학",
            "객체지향프로그래밍", "웹프로그래밍", "모바일앱개발", "인공지능개론", "빅데이터분석"
    };

    private final String[][] SCHEDULES = {
            {"월", "1", "3"}, {"월", "4", "6"},
            {"화", "1", "3"}, {"화", "4", "6"},
            {"수", "1", "3"}, {"수", "4", "6"},
            {"목", "1", "3"}, {"목", "4", "6"},
            {"금", "1", "3"}, {"금", "4", "6"}
    };

    /**
     * 강의 60개 생성 — 연도/학기 분산
     *
     * 연도·학기 분포:
     *   2024-1학기  10개
     *   2024-2학기  10개
     *   2025-1학기  10개
     *   2025-2학기  10개
     *   2026-1학기  20개  ← 현재 학기
     *
     * 각 배치: approved 80% / pending 20%
     * 이수구분: 전공필수/전공선택/교양  (3:4:3 비율)
     * 학점:    1학점 10% / 2학점 20% / 3학점 70%
     * 학년:    1~4학년 균등
     */
    public List<Long> insertLectures(int count, List<Long> professorIds,
                                     List<Long> majorIds, List<Integer> roomIds) {

        // 연도·학기 배치 정의 { year, semester, count, startDate, endDate }
        List<int[]> batches = List.of(
                new int[]{2024, 1, 10},
                new int[]{2024, 2, 10},
                new int[]{2025, 1, 10},
                new int[]{2025, 2, 10},
                new int[]{2026, 1, 20}
        );

        List<Long> lectureIds = new ArrayList<>();
        int nameIdx   = 0;
        int profIdx   = 0;

        for (int[] batch : batches) {
            int year = batch[0], semester = batch[1], batchCount = batch[2];
            LocalDate startDate = semester == 1
                    ? LocalDate.of(year, 3, 1) : LocalDate.of(year, 9, 1);
            LocalDate endDate = semester == 1
                    ? LocalDate.of(year, 6, 20) : LocalDate.of(year, 12, 20);

            Set<String> usedSlots = new HashSet<>(); // 배치별 슬롯 초기화

            for (int i = 0; i < batchCount; i++) {
                LectureCreateReq req = new LectureCreateReq();
                req.setLoginUserId(professorIds.get(profIdx % professorIds.size()));
                req.setMajorId(majorIds.get(nameIdx % majorIds.size()).intValue()); // Long→int
                req.setYear(year);
                req.setSemester(semester);
                req.setLectureName(LECTURE_NAMES[nameIdx % LECTURE_NAMES.length]);
                req.setCredit(pickCredit());
                req.setLectureType(pickType(i));
                req.setAcademicYear((i % 4) + 1);
                req.setMaxStd(faker.number().numberBetween(20, 50));
                req.setGoal(faker.lorem().sentence());
                req.setWeeklyPlan(faker.lorem().sentence());
                req.setStartDate(startDate.toString());  // String으로 변환
                req.setEndDate(endDate.toString());      // String으로 변환

                lectureMapper.createLecture(req);

                String status = (i % 5 == 0) ? "pending" : "approved";
                lectureMapper.updateLectureStatus(req.getLectureId(), status);
                if ("approved".equals(status)) {
                    approvedIds.add(req.getLectureId());
                }
                int[] slot = findAvailableSlot(usedSlots, roomIds, i);
                String[] sched = SCHEDULES[slot[1]];

                LectureScheduleReq schedReq = new LectureScheduleReq();
                schedReq.setLectureId(req.getLectureId());
                schedReq.setRoomId(slot[0]);
                schedReq.setDayOfWeek(sched[0]);
                schedReq.setStartPeriod(Integer.parseInt(sched[1]));
                schedReq.setEndPeriod(Integer.parseInt(sched[2]));
                lectureMapper.createScheduleByRoomId(schedReq);

                lectureIds.add(req.getLectureId());
                nameIdx++;
                profIdx++;
            }
            System.out.printf("✅ %d년 %d학기 강의 %d개 생성%n", year, semester, batchCount);
        }
        return lectureIds;
    }

    private final List<Long> approvedIds = new ArrayList<>();
    public List<Long> getApprovedIds() { return approvedIds; }

    // 학점: 1학점 10% / 2학점 20% / 3학점 70%
    private int pickCredit() {
        int r = (int)(Math.random() * 10);
        if (r < 1) return 1;
        if (r < 3) return 2;
        return 3;
    }

    // 이수구분: 전공필수 30% / 전공선택 40% / 교양 30%
    private String pickType(int idx) {
        int r = idx % 10;
        if (r < 3) return "전공필수";
        if (r < 7) return "전공선택";
        return "교양";
    }

    private int[] findAvailableSlot(Set<String> used, List<Integer> roomIds, int fallbackIdx) {
        for (int si = 0; si < SCHEDULES.length; si++) {
            for (Integer roomId : roomIds) {
                String key = roomId + "_" + SCHEDULES[si][0] + "_" + SCHEDULES[si][1];
                if (!used.contains(key)) {
                    used.add(key);
                    return new int[]{roomId, si};
                }
            }
        }
        // 슬롯 소진 시 순환
        return new int[]{
                roomIds.get(fallbackIdx % roomIds.size()),
                fallbackIdx % SCHEDULES.length
        };
    }
}
