package com.green.greenuni.application.mail;

import com.green.greenuni.application.mail.model.MailCheckReq;
import com.green.greenuni.application.mail.model.PwCodeReq;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping
    public ResultResponse<?> checkMail(@RequestBody MailCheckReq req){
        int result = mailService.findMember(req);
        return new ResultResponse<>("회원 확인", result);
    }

    @PostMapping("/check")
    public ResultResponse<?> checkVerifyCode(@RequestBody PwCodeReq req) {
        Long result = mailService.checkVerifyCode(req);
        return new ResultResponse<>("인증코드 확인", result);
    }
}