package com.green.greenuni.application.member.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberPwResetReq {
    private long memberId;

    @Size(min=10, max=20, message = "비밀번호는 4~20자 사이만 가능합니다.")
    @NotNull(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{10,20}$"
            , message = "비밀번호는 영문자, 숫자, 특수기호로 구성되며 10자 이상이어야 합니다")
    private String password;
}
