package com.sososhopping.entity.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;

//@Entity
@Getter
@DiscriminatorValue("FIX")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixCoupon extends Coupon {

    private Integer fixAmount;

    @Override
    public int getDiscountPrice(int orderPrice) {
        return fixAmount;
    }

}