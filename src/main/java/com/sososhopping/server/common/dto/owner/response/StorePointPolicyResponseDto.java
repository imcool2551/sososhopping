package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.store.Store;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StorePointPolicyResponseDto {
    private Boolean pointPolicyStatus;
    private BigDecimal saveRate;

    public StorePointPolicyResponseDto(Store store) {
        this.pointPolicyStatus = store.getPointPolicyStatus();
        this.saveRate = store.getSaveRate();
    }
}