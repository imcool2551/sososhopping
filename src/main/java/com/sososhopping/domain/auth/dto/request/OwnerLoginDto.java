package com.sososhopping.domain.auth.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class OwnerLoginDto {

    @NotNull(message = "이메일 필수")
    @Email(message = "이메일 형식 오류")
    private String email;

    @NotNull(message = "비밀번호 필수")
    @Length(min = 8, max = 20, message = "비밀번호 8자 이상 20자 이하")
    private String password;
}
