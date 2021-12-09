package com.sososhopping.server.common.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OwnerUpdatePasswordRequest {

    @NotNull
    private String password;

    @NotNull
    @Size(min = 4, message = "비밀번호는 최소 4자리입니다")
    private String newPassword;
}
