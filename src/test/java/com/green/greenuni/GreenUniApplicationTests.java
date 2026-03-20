package com.green.greenuni;

import com.green.greenuni.application.course.CourseMapper;
import com.green.greenuni.application.lectures.LectureMapper;
import com.green.greenuni.application.lectures.model.LectureDto;
import com.green.greenuni.fixture.CourseFixture;
import com.green.greenuni.fixture.LectureFixture;
import com.green.greenuni.fixture.MajorFixture;
import com.green.greenuni.fixture.MemberFixture;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GreenUniApplicationTests {

    @Autowired private MajorFixture majorFixture;
    @Autowired private MemberFixture memberFixture;
    @Autowired
    private LectureFixture lectureFixture;
    @Autowired private CourseFixture courseFixture;
    @Autowired private LectureMapper lectureMapper;
    @Autowired private CourseMapper courseMapper;
    @Autowired private javax.sql.DataSource dataSource;

    // ── 테스트 간 공유 (static 필수) ──────────────────────
    private List<Long> majorIds;
    private List<Integer> roomIds;
    private List<Long> professorIds;
    private List<Long> studentIds;
    private List<Long> lectureIds;
    private List<Long> approvedLectureIds;

    @Test @Order(0)
    @DisplayName("DB 초기화")
    void cleanUp() throws Exception {
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE TABLE attendance");
            stmt.execute("TRUNCATE TABLE course");
            stmt.execute("TRUNCATE TABLE lecture_schedule");
            stmt.execute("TRUNCATE TABLE lecture");
            stmt.execute("TRUNCATE TABLE student");
            stmt.execute("TRUNCATE TABLE professor");
            stmt.execute("TRUNCATE TABLE staff");
            stmt.execute("TRUNCATE TABLE member");
            stmt.execute("TRUNCATE TABLE major");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
        System.out.println("✅ DB 초기화 완료");
    }
    // ─────────────────────────────────────────────────────
    // Order 1 : 전공 10개 생성 (정상 8 / 폐지 2)
    // ─────────────────────────────────────────────────────
    @Test @Order(1)
    @DisplayName("전공 10개 생성 (정상 8개 / 폐지 2개)")
    void insertMajors() {
        majorIds = majorFixture.insertMajors();
        assertEquals(8, majorIds.size(), "정상 전공은 8개여야 합니다");
    }

    // ─────────────────────────────────────────────────────
    // Order 2 : 강의실 조회 (lecture_room은 이미 존재한다고 가정)
    // ─────────────────────────────────────────────────────
    @Test @Order(2)
    @DisplayName("강의실 조회")
    void loadRooms() {
        roomIds = lectureMapper.getAllRoomIds();         // List<Integer> 반환
        assumeTrue(!roomIds.isEmpty(), "lecture_room 데이터 없음");
        System.out.println("✅ 강의실 수: " + roomIds.size());
    }

    // ─────────────────────────────────────────────────────
    // Order 3 : 교수 30명 (재직 24 / 휴직 4 / 퇴직 2)
    // ─────────────────────────────────────────────────────
    @Test @Order(3)
    @DisplayName("교수 30명 생성")
    void insertProfessors() {
        assumeTrue(majorIds != null, "Order(1) 먼저 실행 필요");  // 추가
        professorIds = memberFixture.insertProfessors(30, majorIds);
    }

    // ─────────────────────────────────────────────────────
    // Order 4 : 학생 120명 (재학 72 / 휴학 24 / 졸업 12 / 자퇴 7 / 퇴학 5)
    // ─────────────────────────────────────────────────────
    @Test @Order(4)
    @DisplayName("학생 120명 생성")
    void insertStudents() {
        assumeTrue(majorIds != null, "Order(1) 먼저 실행 필요");
        studentIds = memberFixture.insertStudents(120, majorIds);
        assertEquals(120, studentIds.size());
    }

    // ─────────────────────────────────────────────────────
    // Order 5 : 관리자(staff) 5명 (재직 4 / 휴직 1)
    // ─────────────────────────────────────────────────────
    @Test @Order(5)
    @DisplayName("관리자 5명 생성")
    void insertStaff() {
        List<Long> staffIds = memberFixture.insertStaff(5);
        assertEquals(5, staffIds.size());
    }

    // ─────────────────────────────────────────────────────
    // Order 6 : 강의 60개 생성
    //   2024-1  10개 / 2024-2  10개
    //   2025-1  10개 / 2025-2  10개
    //   2026-1  20개
    //   이수구분: 전공필수 30% / 전공선택 40% / 교양 30%
    //   학점:    3학점 70% / 2학점 20% / 1학점 10%
    //   학년:    1~4학년 균등
    // ─────────────────────────────────────────────────────
    @Test @Order(6)
    @DisplayName("강의 60개 생성 (연도·학기 분산)")
    void insertLectures() {
        assumeTrue(professorIds != null, "Order(3) 먼저 실행 필요");
        assumeTrue(roomIds != null,      "Order(2) 먼저 실행 필요");

        lectureIds = lectureFixture.insertLectures(60, professorIds, majorIds, roomIds);
        assertEquals(60, lectureIds.size());

        approvedLectureIds = lectureMapper.getApprovedLectureIds(lectureIds);
        System.out.println("✅ 강의 60개 - approved: " + approvedLectureIds.size()
                + " / pending: " + (lectureIds.size() - approvedLectureIds.size()));
    }

    // ─────────────────────────────────────────────────────
    // Order 7 : 수강신청 (approved 강의에 학생 랜덤 배정)
    // ─────────────────────────────────────────────────────
    @Test @Order(7)
    @DisplayName("수강신청")
    void insertCourses() {
        assumeTrue(approvedLectureIds != null, "Order(6) 먼저 실행 필요");
        assumeTrue(studentIds != null,         "Order(4) 먼저 실행 필요");
        courseFixture.insertCourses(approvedLectureIds, studentIds);
    }

    // ─────────────────────────────────────────────────────
    // Order 8 : 출결 — approved 강의 전체, 15주치
    //   출석 67% / 지각 20% / 결석 13%
    // ─────────────────────────────────────────────────────
    @Test @Order(8)
    @DisplayName("출결 데이터 생성 (15주치)")
    void insertAttendances() {
        assumeTrue(approvedLectureIds != null, "Order(6) 먼저 실행 필요");

        for (Long lectureId : approvedLectureIds) {
            Map<Long, Long> courseMap = buildCourseMap(lectureId);
            if (courseMap.isEmpty()) continue;

            // 강의 연도·학기로 학기 시작일 결정
            LectureDto lecture = lectureMapper.getLectureById(lectureId);
            LocalDate semesterStart = lecture.getSemester() == 1
                    ? LocalDate.of(lecture.getYear(), 3, 3)   // 1학기 3월 첫째 주
                    : LocalDate.of(lecture.getYear(), 9, 1);  // 2학기 9월 첫째 주

            courseFixture.insertAttendances(lectureId, courseMap, semesterStart);
        }
        System.out.println("✅ 출결 생성 완료");
    }

    // ─────────────────────────────────────────────────────
    // Order 9 : 성적 — course 테이블 직접 업데이트
    //   중간고사 + 기말고사 + 과제 + 출결 → total_score + grade_letter
    //   B/C 학점 비중 자연스럽게 높음
    // ─────────────────────────────────────────────────────
    @Test @Order(9)
    @DisplayName("성적 데이터 생성")
    void insertGrades() {
        assumeTrue(approvedLectureIds != null, "Order(6) 먼저 실행 필요");

        for (Long lectureId : approvedLectureIds) {
            Map<Long, Long> courseMap = buildCourseMap(lectureId);
            if (courseMap.isEmpty()) continue;
            courseFixture.insertGrades(courseMap);
        }
        System.out.println("✅ 성적 생성 완료");
    }

    // ─────────────────────────────────────────────────────
    // 헬퍼: { memberId → courseId } 배치 조회 (N+1 방지)
    // ─────────────────────────────────────────────────────
    private Map<Long, Long> buildCourseMap(Long lectureId) {
        List<Map<String, Object>> rows = courseMapper.getCourseMap(lectureId);
        return rows.stream().collect(Collectors.toMap(
                row -> ((Number) row.get("member_id")).longValue(),
                row -> ((Number) row.get("course_id")).longValue()
        ));
    }
}
