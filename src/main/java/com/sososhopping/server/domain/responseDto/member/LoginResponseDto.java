package com.sososhopping.server.domain.responseDto.member;

import lombok.*;

/**
 * 점주 로그인 응답
 */
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
}
