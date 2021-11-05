package com.sososhopping.server.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreBusinessDayRequestDto {
    private String day;
    private Boolean isOpen;
    private String openTime;
    private String closeTime;
}
