package com.sososhopping.common.dto.owner.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreUserPointResponseDto {
    private String userName;
    private Integer userPoint;
}
