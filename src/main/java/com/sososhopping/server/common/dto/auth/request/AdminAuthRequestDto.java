package com.sososhopping.server.common.dto.auth.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
public class AdminAuthRequestDto {
    private String nickname;
    private String password;
}
