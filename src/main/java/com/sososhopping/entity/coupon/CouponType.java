package com.sososhopping.entity.coupon;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;

public enum CouponType {
    FIX((orderPrice, amount) -> amount),
    RATE((orderPrice, amount) -> (int) (orderPrice * (amount / 100.0)));

    private BinaryOperator<Integer> discountPriceCalculator;

    CouponType(BinaryOperator<Integer> discountPriceCalculator) {
        this.discountPriceCalculator = discountPriceCalculator;
    }

    public static CouponType of(String name) {
        return Arrays.stream(CouponType.values())
                .filter(couponType -> couponType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("no coupon type of name " + name));
    }

    public int calculateDiscountPrice(int orderPrice, int amount) {
        return discountPriceCalculator.apply(orderPrice, amount);
    }
}
