package com.green.greenuni.fixture;

import com.green.greenuni.application.course.model.CoursePostReq;
import com.green.greenuni.application.grade.GradeMapper;
import net.datafaker.Faker;
import com.green.greenuni.application.attendance.AttendanceMapper;
import com.green.greenuni.application.attendance.model.AttendSetReq;
import com.green.greenuni.application.course.CourseMapper;
import com.green.greenuni.application.grade.model.GradeUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

// fixture/CourseFixture.java
@Component
public class CourseFixture {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired private AttendanceMapper attendanceMapper;
    @Autowired private GradeMapper gradeMapper;

    private final Faker faker = new Faker();

    // ──────────────────────────────────────────
    // 수강신청
    // ──────────────────────────────────────────
    public void insertCourses(List<Long> approvedLectureIds, List<Long> studentIds) {
        int total = 0;
        for (Long lectureId : approvedLectureIds) {
            int enrollCount = faker.number().numberBetween(5, 15);
            List<Long> shuffled = new ArrayList<>(studentIds);
            Collections.shuffle(shuffled);

            for (int i = 0; i < Math.min(enrollCount, shuffled.size()); i++) {
                try {
                    CoursePostReq req = new CoursePostReq();
                    req.setLectureId(lectureId);
                    req.setMemberId(shuffled.get(i));
                    courseMapper.saveCourse(req);
                    courseMapper.decreaseRemStd(lectureId);
                    total++;
                } catch (Exception e) {
                    // 중복 수강신청 무시
                }
            }
        }
        System.out.println("✅ 수강신청 완료: 총 " + total + "건");
    }

    // ──────────────────────────────────────────
    // 출결 — 한 학기 15주치
    // 출석 70% / 지각 20% / 결석 10%
    // attendance: lecture_id, course_id, member_id, attend_date, status
    // ──────────────────────────────────────────
    public void insertAttendances(Long lectureId, Map<Long, Long> courseMap,
                                  LocalDate semesterStart) {
        // 매주 월요일 기준 15주
        List<LocalDate> dates = new ArrayList<>();
        for (int week = 0; week < 15; week++) {
            dates.add(semesterStart.plusWeeks(week));
        }

        // 출석 비중 높게: 14개 중 attend 10, late 3, absent 1
        String[] statusPool = {
                "attend","attend","attend","attend","attend",
                "attend","attend","attend","attend","attend",
                "late","late","late","absent","absent"
        };

        int count = 0;
        for (LocalDate date : dates) {
            for (Map.Entry<Long, Long> entry : courseMap.entrySet()) {
                AttendSetReq req = new AttendSetReq();
                req.setLectureId(lectureId);
                req.setCourseId(entry.getValue());
                req.setMemberId(entry.getKey());
                req.setAttendDate(date);
                req.setStatus(statusPool[(int)(Math.random() * statusPool.length)]);
                attendanceMapper.setAttendListByReq(req);
                count++;
            }
        }
        System.out.println("  → 강의 " + lectureId + " 출결 " + count + "건 생성");
    }

    // ──────────────────────────────────────────
    // 성적 — course 테이블 직접 업데이트
    // 정규분포 느낌으로 B/C 학점 비중 높게
    //
    // 점수 범위:
    //   중간고사  20~45점
    //   기말고사  20~45점
    //   과제      5~10점
    //   출결      5~10점
    //   합계      50~110점 → A+~F
    // ──────────────────────────────────────────
    public void insertGrades(Map<Long, Long> courseMap) {
        for (Long courseId : courseMap.values()) {
            int mid    = weightedScore(20, 45);
            int fin    = weightedScore(20, 45);
            int assign = faker.number().numberBetween(5, 11);
            int attend = faker.number().numberBetween(5, 11);
            int total  = mid + fin + assign + attend;

            GradeUpdateReq req = new GradeUpdateReq();
            req.setCourseId(courseId);
            req.setMidScore(mid);
            req.setFinScore(fin);
            req.setAssignmentScore(assign);
            req.setAttendScore(attend);
            gradeMapper.updateGrade(req);
        }
    }

    /**
     * 중간값 근처 점수 비중을 높이는 가중 랜덤
     * (단순 uniform 대신 중간 점수대 B/C 학점 비중 자연스럽게)
     */
    private int weightedScore(int min, int max) {
        int mid = (min + max) / 2;
        int r1 = faker.number().numberBetween(min, max + 1);
        int r2 = faker.number().numberBetween(min, max + 1);
        // 두 값의 평균 → 중간값 쪽으로 몰림
        return (r1 + r2) / 2;
    }

    private String toGradeLetter(int total) {
        if (total >= 95) return "A+";
        if (total >= 90) return "A";
        if (total >= 85) return "B+";
        if (total >= 80) return "B";
        if (total >= 75) return "C+";
        if (total >= 70) return "C";
        if (total >= 65) return "D+";
        if (total >= 60) return "D";
        return "F";
    }
}
