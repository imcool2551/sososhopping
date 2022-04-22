package com.sososhopping.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StorePointPolicyRequestDto {
    private Boolean pointPolicyStatus;
    private BigDecimal saveRate;
}