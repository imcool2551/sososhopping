package com.sososhopping.auth.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserSignUpRequestDto {

    @Email
    private String email;

    @Length(min = 8)
    private String password;

    @Length(min = 2)
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String nickname;

    @NotBlank
    private String street;

    @NotBlank
    private String detail;
}
