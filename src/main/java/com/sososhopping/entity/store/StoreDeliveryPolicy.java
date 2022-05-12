package com.sososhopping.entity.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDeliveryPolicy {

    @Column(columnDefinition = "tinyint")
    private boolean deliveryStatus;

    private Integer minimumOrderPrice;

    private Integer deliveryCharge;

    public StoreDeliveryPolicy(boolean deliveryStatus, Integer minimumOrderPrice, Integer deliveryCharge) {
        this.deliveryStatus = deliveryStatus;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryCharge = deliveryCharge;
    }
}
