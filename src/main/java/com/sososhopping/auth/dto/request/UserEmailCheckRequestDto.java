package com.sososhopping.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserEmailCheckRequestDto {

    @NotNull(message = "이메일 필수")
    @Email(message = "이메일 형식 오류")
    private String email;
}
