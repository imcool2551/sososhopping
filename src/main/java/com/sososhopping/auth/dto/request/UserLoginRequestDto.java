package com.sososhopping.auth.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginRequestDto {

    @NotNull(message = "이메일 필수")
    @Email(message = "이메일 형식 오류")
    private String email;

    @NotBlank(message = "비밀번호 필수")
    @Length(min = 8, message = "비밀번호 8자 이상")
    private String password;
}
