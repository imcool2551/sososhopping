package com.sososhopping.entity.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import java.math.BigDecimal;

//@Entity
@Getter
@DiscriminatorValue("RATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RateCoupon extends Coupon {

    private BigDecimal rateAmount;

    @Override
    public int getDiscountPrice(int orderPrice) {
        return (int)(orderPrice * rateAmount.doubleValue() / 100);
    }

}