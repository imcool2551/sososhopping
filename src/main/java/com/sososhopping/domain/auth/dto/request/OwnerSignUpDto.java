package com.sososhopping.domain.auth.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class OwnerSignUpDto {

    @NotNull(message = "이메일 필수")
    @Email(message = "이메일 형식 오류")
    private String email;

    @NotNull(message = "비밀번호 필수")
    @Length(min = 8, max = 20, message = "비밀번호 8자 이상 20자 이하")
    private String password;

    @NotNull(message = "이름 필수")
    @Length(min = 2, max = 10, message = "이름 2자 이상 10자 이하")
    private String name;

    @NotNull(message = "핸드폰 필수")
    @Length(min = 11, max = 11, message = "핸드폰 번호 11자")
    private String phone;
}
