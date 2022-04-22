package com.sososhopping.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCouponUsageRequestDto {
    private String phone;
    private String couponCode;
}
