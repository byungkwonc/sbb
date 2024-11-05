package com.napico.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordForm {
    @NotEmpty(message="회원 정보가 비정상입니다.")
    private String userid;

    @NotEmpty(message="현재 비밀번호를 입력해 주세요.")
    private String password;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;
}
