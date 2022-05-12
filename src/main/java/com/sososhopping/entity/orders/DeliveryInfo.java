package com.sososhopping.entity.orders;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryInfo {

    private Integer deliveryCharge;

    private String deliveryStreetAddress;

    private String deliveryDetailedAddress;

    public DeliveryInfo(Integer deliveryCharge, String deliveryStreetAddress, String deliveryDetailedAddress) {
        this.deliveryCharge = deliveryCharge;
        this.deliveryStreetAddress = deliveryStreetAddress;
        this.deliveryDetailedAddress = deliveryDetailedAddress;
    }
}
