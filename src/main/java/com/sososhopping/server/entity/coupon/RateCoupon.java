package com.sososhopping.server.entity.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RATE")
public class RateCoupon extends Coupon {

    private Double rateAmount;

    @Override
    protected int getDiscountPrice() {
        return 0;
    }
}
