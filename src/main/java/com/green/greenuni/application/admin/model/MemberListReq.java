package com.green.greenuni.application.admin.model;

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
    private String majorName;
    private String memberName;
    private int startIdx;

    public MemberListReq(int page, int size, String role, String majorName, String memberName) {
        this.page = page;
        this.size = size;
        this.role = role;
        this.majorName = majorName;
        this.memberName = memberName;
        this.startIdx = (page - 1) * size;
    }
}
