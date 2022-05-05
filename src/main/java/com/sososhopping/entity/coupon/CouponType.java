package com.sososhopping.entity.coupon;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;

public enum CouponType {
    FIX((orderPrice, amount) -> amount),
    RATE((orderPrice, amount) -> (int) (orderPrice * (amount / 100.0)));

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

    public int calculateDiscountPrice(int orderPrice, int amount) {
        return binaryOperator.apply(orderPrice, amount);
    }
}
