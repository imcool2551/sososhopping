package com.sososhopping.entity.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLocation {

    private BigDecimal lat;

    private BigDecimal lng;

    private String streetAddress;

    private String detailedAddress;

    public StoreLocation(BigDecimal lat, BigDecimal lng, String streetAddress, String detailedAddress) {
        this.lat = lat;
        this.lng = lng;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
    }
}
