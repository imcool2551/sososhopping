package com.sososhopping.domain.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DuplicatePhoneCheckDto {

    @NotNull(message = "핸드폰 필수")
    @NotBlank(message = "핸드폰 필수")
    private String phone;
}
