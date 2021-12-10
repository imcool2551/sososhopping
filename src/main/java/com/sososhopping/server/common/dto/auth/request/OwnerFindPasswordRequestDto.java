package com.sososhopping.server.common.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OwnerFindPasswordRequestDto {

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String phone;
}
