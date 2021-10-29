package com.sososhopping.server.domain.entity.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FIX")
public class FixCoupon extends Coupon {

    private Integer fixAmount;

    @Override
    protected int getDiscountPrice() {
        return fixAmount;
    }
}