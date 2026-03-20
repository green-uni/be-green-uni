package com.green.greenuni.fixture;

import com.green.greenuni.application.admin.model.MemberCreateReq;
import com.green.greenuni.application.member.MemberMapper;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MemberFixture {

    @Autowired private MemberMapper memberMapper;

    private final Faker faker = new Faker(new Locale("ko"));
    private int professorCodeSeq = 0;
    private int studentCodeSeq   = 0;
    private int staffCodeSeq     = 0;

    private String nextCode(String prefix, int seq) {
        return String.format("%s2026%03d", prefix, seq);
    }

    public List<Long> insertProfessors(int count, List<Long> majorIds) {
        List<String> statusPool = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if      (i < count * 0.80) statusPool.add("재직");
            else if (i < count * 0.93) statusPool.add("휴직");
            else                       statusPool.add("퇴직");
        }
        Collections.shuffle(statusPool);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MemberCreateReq req = new MemberCreateReq();
            req.setRole("professor");
            req.setCode(nextCode("P", ++professorCodeSeq));
            req.setPassword("1234");
            req.setName(faker.name().fullName());
            req.setBirth(faker.date().birthday(35, 65)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            req.setEntryDate(LocalDate.now()
                    .minusYears(faker.number().numberBetween(1, 25)).toString());
            req.setEmail(faker.internet().emailAddress());
            req.setTel(faker.phoneNumber().cellPhone());
            req.setMajorId(majorIds.get(i % majorIds.size()).intValue());
            req.setDegree(faker.options().option("박사", "석사"));
            req.setPosition(faker.options().option("교수", "부교수", "조교수"));
            req.setLabRoom("공학관 " + faker.number().numberBetween(100, 500) + "호");
            req.setLabTel("02-" + faker.number().numberBetween(1000, 9999)
                    + "-" + faker.number().numberBetween(1000, 9999));
            req.setStatus(statusPool.get(i));

            memberMapper.createMember(req);
            memberMapper.updateMemberCodeAPw(req);
            memberMapper.createProfessor(req);

            ids.add(req.getMemberId());
        }
        printStatusSummary("교수", statusPool);
        return ids;
    }

    public List<Long> insertStudents(int count, List<Long> majorIds) {
        List<String> statusPool = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if      (i < count * 0.60) statusPool.add("재학");
            else if (i < count * 0.80) statusPool.add("휴학");
            else if (i < count * 0.90) statusPool.add("졸업");
            else if (i < count * 0.96) statusPool.add("자퇴");
            else                       statusPool.add("퇴학");
        }
        Collections.shuffle(statusPool);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MemberCreateReq req = new MemberCreateReq();
            req.setRole("student");
            req.setCode(nextCode("S", ++studentCodeSeq));
            req.setPassword("1234");
            req.setName(faker.name().fullName());
            req.setBirth(faker.date().birthday(18, 30)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            req.setEntryDate(LocalDate.now()
                    .minusYears(faker.number().numberBetween(0, 5)).toString());
            req.setEmail(faker.internet().emailAddress());
            req.setTel(faker.phoneNumber().cellPhone());
            req.setMajorId(majorIds.get(i % majorIds.size()).intValue());
            req.setAcademicYear(faker.number().numberBetween(1, 5));
            req.setSemester(faker.number().numberBetween(1, 2));
            req.setStatus(statusPool.get(i));

            memberMapper.createMember(req);
            memberMapper.updateMemberCodeAPw(req);
            memberMapper.createStudent(req);

            ids.add(req.getMemberId());
        }
        printStatusSummary("학생", statusPool);
        return ids;
    }

    public List<Long> insertStaff(int count) {
        List<String> statusPool = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            statusPool.add(i < count * 0.8 ? "재직" : "휴직");
        }
        Collections.shuffle(statusPool);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MemberCreateReq req = new MemberCreateReq();
            req.setRole("staff");
            req.setCode(nextCode("A", ++staffCodeSeq));
            req.setPassword("1234");
            req.setName(faker.name().fullName());
            req.setBirth(faker.date().birthday(25, 55)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            req.setEntryDate(LocalDate.now()
                    .minusYears(faker.number().numberBetween(1, 15)).toString());
            req.setEmail(faker.internet().emailAddress());
            req.setTel(faker.phoneNumber().cellPhone());
            req.setStatus(statusPool.get(i));

            memberMapper.createMember(req);
            memberMapper.updateMemberCodeAPw(req);
            memberMapper.createStaff(req);

            ids.add(req.getMemberId());
        }
        printStatusSummary("관리자", statusPool);
        return ids;
    }

    private void printStatusSummary(String role, List<String> pool) {
        Map<String, Long> summary = pool.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println("✅ " + role + " 생성 완료: " + summary);
    }
}