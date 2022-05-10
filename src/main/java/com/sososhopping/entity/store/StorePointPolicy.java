package com.sososhopping.entity.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StorePointPolicy {

    @Column(columnDefinition = "tinyint")
    private boolean pointPolicyStatus;

    private BigDecimal saveRate;

    public StorePointPolicy(boolean pointPolicyStatus, BigDecimal saveRate) {
        this.pointPolicyStatus = pointPolicyStatus;
        this.saveRate = saveRate;
    }

    public void updateSaveRate(boolean pointPolicyStatus, BigDecimal saveRate) {
        this.pointPolicyStatus = pointPolicyStatus;
        this.saveRate = saveRate;
    }
}
