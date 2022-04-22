package com.sososhopping.domain.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserNicknameCheckDto {

    @NotNull(message = "닉네임 필수")
    @NotBlank(message = "닉네임 필수")
    private String nickname;
}
