package com.sososhopping.server.entity.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("RATE")
public class RateCoupon extends Coupon {

    private BigDecimal rateAmount;

    @Override
    protected int getDiscountPrice() {
        return 0;
    }
}