package com.sososhopping.auth.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserSignUpRequestDto {

    @Email(message = "이메일 형식 오류")
    private String email;

    @Length(min = 8, message = "비밀번호 8자 이상")
    private String password;

    @Length(min = 2, message = "이름 2자 이상")
    private String name;

    @NotBlank(message = "핸드폰 필수")
    private String phone;

    @NotBlank(message = "닉네임 필수")
    private String nickname;

    @NotBlank(message = "도로 주소 필수")
    private String street;

    @NotBlank(message = "상세 주소 필수")
    private String detail;
}
