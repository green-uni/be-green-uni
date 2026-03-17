package com.green.greenuni.application.mail.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailCheckReq {
    private String code;
    private String email;
}
