package com.sososhopping.common.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OwnerFindEmailRequestDto {

    @NotNull
    private String name;

    @NotNull
    private String phone;
}
