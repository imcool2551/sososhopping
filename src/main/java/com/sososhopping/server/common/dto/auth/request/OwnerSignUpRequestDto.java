package com.sososhopping.server.common.dto.auth.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 점주 회원가입 요청 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OwnerSignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;
}
