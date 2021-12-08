package com.sososhopping.server.common.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserChangePasswordDto {

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야합니다")
    private String password;
}
