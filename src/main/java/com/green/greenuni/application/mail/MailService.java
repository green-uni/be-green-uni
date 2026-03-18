package com.green.greenuni.application.mail;

import com.green.greenuni.application.mail.model.MailCheckReq;
import com.green.greenuni.application.mail.model.PwCodeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {
    private final MailMapper mailMapper;
    private final JavaMailSender mailSender;

    // 인증코드 임시저장
    private final Map<String, String> codeStore = new HashMap<>();
    // 멤버ID 임시저장
    private final Map<String, Long> memberIdStore = new HashMap<>();

    int findMember(MailCheckReq req){
        // req로 DB 조회
        Long memberId = mailMapper.findMember(req);
        // DB에 없다면 예외 처리
        if(memberId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "정보가 일치하지 않습니다");
        }
        // DB에 있다면 메일 발송
        // 5자리 랜덤 인증코드 생성
        String verifyCode = String.format("%05d", (int)(Math.random() * 90000) + 10000);
        // 인증코드, 조회된 멤버 아이디 임시저장
        codeStore.put(req.getEmail(), verifyCode);
        memberIdStore.put(req.getEmail(), memberId);

        // 메일 발송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(req.getEmail());
        message.setSubject("[그린대학교] 비밀번호 재설정 인증코드");
        message.setText("인증코드: " + verifyCode);
        mailSender.send(message);

        return 1;
    }

    public Long checkVerifyCode(PwCodeReq req) {
        // codeStore에서 이메일로 저장된 코드 꺼내기
        String savedVerifyCode = codeStore.get(req.getEmail());

        // 코드가 없거나 (만료) 일치하지 않으면 예외
        if (savedVerifyCode == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "인증코드가 만료되었습니다.");
        }
        if (!savedVerifyCode.equals(req.getVerifyCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다.");
        }

        // 인증번호 확인 후 인증코드 삭제
        codeStore.remove(req.getEmail());

        // memberId 꺼내서 반환
        Long memberId = memberIdStore.get(req.getEmail());
        memberIdStore.remove(req.getEmail());  // 삭제
        return memberId;
    }
}
