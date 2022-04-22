package com.sososhopping.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserNicknameCheckRequestDto {

    @NotBlank(message = "닉네임 필수")
    private String nickname;
}
