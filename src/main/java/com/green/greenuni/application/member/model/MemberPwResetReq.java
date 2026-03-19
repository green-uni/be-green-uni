package com.green.greenuni.application.member.model;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 10, max = 20, message = "비밀번호는 10자 이상 20자 이하이어야 합니다.")
    @Pattern(
            // 최소 하나의 영문, 숫자, 특수문자를 포함하는지 확인 (길이는 @Size에서 제어)
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]*$",
            message = "비밀번호는 영문자, 숫자, 특수기호가 각각 최소 하나 이상 포함되어야 합니다."
    )
    private String password;
}
