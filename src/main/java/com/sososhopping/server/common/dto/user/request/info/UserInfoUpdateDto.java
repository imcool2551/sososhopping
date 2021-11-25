package com.sososhopping.server.common.dto.user.request.info;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UserInfoUpdateDto {

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야합니다")
    private String password;

    @NotNull
    private String nickname;

    @NotNull
    private String streetAddress;

    @NotNull
    private String detailedAddress;
}
