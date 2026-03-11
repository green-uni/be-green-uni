package com.green.greenuni.application.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberListReq {
    private int page;
    private int size; // 한 목록에 몇개씩
    private String role;
    private int startIdx;

    public MemberListReq(int page, int size, String role) {
        this.page = page;
        this.size = size;
        this.role = role;
        this.startIdx = (page - 1) * size;
    }
}
