package com.sososhopping.server.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPointUpdateRequestDto {
    private String phone;
    private Integer pointAmount;
    private Boolean isSave;
}
