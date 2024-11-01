package com.napico.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForm {
    @NotEmpty(message="이메일은 필수 항목 입니다.")
    @Email
    private String email;
}
