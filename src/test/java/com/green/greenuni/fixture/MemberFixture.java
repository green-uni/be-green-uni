package com.green.greenuni.fixture;

import com.green.greenuni.application.admin.model.MemberCreateReq;
import com.green.greenuni.application.member.MemberMapper;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MemberFixture {

    @Autowired private MemberMapper memberMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker(new Locale("ko"));
    private int professorCodeSeq = 0;
    private int studentCodeSeq   = 0;
    private int staffCodeSeq     = 0;

    // 코드 생성: [입학년도4] + [구분코드1] + [순번3]
    // 학생=1, 교수=2, 관리자=3
    private String nextCode(String role, int seq, String entryDate) {
        String year = entryDate.substring(0, 4);
        String roleCode = switch (role) {
            case "professor" -> "2";
            case "admin"     -> "3";
            default          -> "1"; // student
        };
        return year + roleCode + String.format("%03d", seq);
    }

    public List<Long> insertProfessors(int count, List<Long> majorIds) {
        List<String> statusPool = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if      (i < count * 0.80) statusPool.add("employment");
            else if (i < count * 0.93) statusPool.add("absence");
            else                       statusPool.add("retirement");
        }
        Collections.shuffle(statusPool);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String entryDate = LocalDate.now()
                    .minusYears(faker.number().numberBetween(1, 25)).toString();
            String birth = faker.date().birthday(35, 65)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();

            MemberCreateReq req = new MemberCreateReq();
            req.setRole("professor");
            req.setPassword(passwordEncoder.encode(birth.replace("-", "")));
            req.setName(faker.name().fullName());
            req.setBirth(birth);
            req.setEntryDate(entryDate);
            req.setEmail(faker.internet().emailAddress());  // 영문 이메일
            req.setTel(faker.number().digits(11));          // 숫자만 11자리
            req.setMajorId(majorIds.get(i % majorIds.size()).intValue());
            req.setDegree(faker.options().option("박사", "석사"));
            req.setPosition(faker.options().option("교수", "부교수", "조교수"));
            req.setLabRoom("공학관 " + faker.number().numberBetween(100, 500) + "호");
            req.setLabTel("0" + faker.number().digits(10));
            req.setStatus(statusPool.get(i));

            memberMapper.createMember(req);

            req.setCode(nextCode("professor", ++professorCodeSeq, entryDate));
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
            if      (i < count * 0.60) statusPool.add("enrolled");
            else if (i < count * 0.80) statusPool.add("absence");
            else if (i < count * 0.90) statusPool.add("graduation");
            else if (i < count * 0.96) statusPool.add("quit");
            else                       statusPool.add("expulsion");
        }
        Collections.shuffle(statusPool);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String entryDate = LocalDate.now()
                    .minusYears(faker.number().numberBetween(0, 5)).toString();
            String birth = faker.date().birthday(18, 30)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();

            MemberCreateReq req = new MemberCreateReq();
            req.setRole("student");
            req.setPassword(passwordEncoder.encode(birth.replace("-", "")));
            req.setName(faker.name().fullName());
            req.setBirth(birth);
            req.setEntryDate(entryDate);
            req.setEmail(faker.internet().emailAddress());
            req.setTel(faker.number().digits(11));
            req.setMajorId(majorIds.get(i % majorIds.size()).intValue());
            req.setAcademicYear(faker.number().numberBetween(1, 5));
            req.setSemester(faker.number().numberBetween(1, 2));
            req.setStatus(statusPool.get(i));

            memberMapper.createMember(req);

            req.setCode(nextCode("student", ++studentCodeSeq, entryDate));
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
            if      (i < count * 0.80) statusPool.add("employment");
            else if (i < count * 0.93) statusPool.add("absence");
            else                       statusPool.add("retirement");
        }
        Collections.shuffle(statusPool);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String entryDate = LocalDate.now()
                    .minusYears(faker.number().numberBetween(1, 15)).toString();
            String birth = faker.date().birthday(25, 55)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();

            MemberCreateReq req = new MemberCreateReq();
            req.setRole("admin");
            req.setPassword(passwordEncoder.encode(birth.replace("-", "")));
            req.setName(faker.name().fullName());
            req.setBirth(birth);
            req.setEntryDate(entryDate);
            req.setEmail(faker.internet().emailAddress());
            req.setTel(faker.number().digits(11));
            req.setStatus(statusPool.get(i));

            memberMapper.createMember(req);

            req.setCode(nextCode("admin", ++staffCodeSeq, entryDate));
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