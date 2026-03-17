package com.green.greenuni.application.mail;

import com.green.greenuni.application.mail.model.MailCheckReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailMapper {
    Long findMember(MailCheckReq req);
}
