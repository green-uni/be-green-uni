package com.green.greenuni.application.mail.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class PwCodeReq {
    private String email;
    private String verifyCode;
}
