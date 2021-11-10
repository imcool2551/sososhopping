package com.sososhopping.server.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCouponRequestDto {
    private String couponName;
    private Integer stockQuantity;
    private Integer minimumOrderPrice;
    private String issuedStartDate;
    private String issuedDueDate;
    private String expiryDate;
    private String couponType;
    private BigDecimal rateAmount;
    private Integer fixAmount;
}
