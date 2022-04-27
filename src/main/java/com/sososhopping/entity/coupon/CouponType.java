package com.sososhopping.entity.coupon;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;

public enum CouponType {
    FIX((originalPrice, couponAmount) -> couponAmount),
    RATE((originalPrice, couponAmount) -> originalPrice * (couponAmount / 100));

    BinaryOperator<Integer> binaryOperator;

    CouponType(BinaryOperator<Integer> binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public static CouponType of(String name) {
        return Arrays.stream(CouponType.values())
                .filter(couponType -> couponType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("no coupon type of name " + name));
    }

    public int getDiscountPrice(int originalPrice, int couponAmount) {
        return binaryOperator.apply(originalPrice, couponAmount);
    }
}
