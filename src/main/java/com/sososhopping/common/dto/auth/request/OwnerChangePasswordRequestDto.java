package com.sososhopping.common.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OwnerChangePasswordRequestDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
