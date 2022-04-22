package com.sososhopping.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserPhoneCheckRequestDto {

    @NotNull(message = "핸드폰 필수")
    private String phone;
}
