package com.sososhopping.domain.user.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserInfoUpdateDto {

    @NotNull(message = "이름 필수")
    @Length(min = 2, max = 10, message = "이름 2자 이상 10자 이하")
    private String name;

    @NotNull(message = "핸드폰 필수")
    @Length(min = 11, max = 11, message = "핸드폰 번호 11자")
    private String phone;

    @NotNull(message = "닉네임 필수")
    @NotBlank(message = "닉네임 필수")
    private String nickname;

    @NotNull(message = "도로 주소 필수")
    @NotBlank(message = "도로 주소 필수")
    private String streetAddress;

    @NotNull(message = "상세 주소 필수")
    @NotBlank(message = "상세 주소 필수")
    private String detailedAddress;
}
