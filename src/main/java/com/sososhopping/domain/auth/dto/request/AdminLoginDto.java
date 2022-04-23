package com.sososhopping.domain.auth.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AdminLoginDto {

    @NotNull(message = "닉네임 필수")
    @NotBlank(message = "닉네임 필수")
    private String nickname;

    @NotNull(message = "비밀번호 필수")
    @NotBlank(message = "비밀번호 필수")
    private String password;
}
