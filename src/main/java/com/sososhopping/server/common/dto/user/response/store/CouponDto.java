package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.coupon.FixCoupon;
import com.sososhopping.server.entity.coupon.RateCoupon;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CouponDto {

    private Long couponId;
    private String storeName;
    private String couponName;
    private Integer stockQuantity;
    private String couponCode;
    private Integer minimumOrderPrice;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private String couponType;
    private Integer fixAmount;
    private BigDecimal rateAmount;

    public CouponDto(Coupon coupon) {
        couponId = coupon.getId();
        storeName = coupon.getStoreName();
        couponName = coupon.getCouponName();
        stockQuantity = coupon.getStockQuantity();
        couponCode = coupon.getCouponCode();
        minimumOrderPrice = coupon.getMinimumOrderPrice();
        startDate = coupon.getStartDate();
        dueDate = coupon.getDueDate();
        couponType = coupon.getCouponType();
        if (coupon instanceof FixCoupon) {
            fixAmount = ((FixCoupon) coupon).getFixAmount();
        } else if (coupon instanceof RateCoupon) {
            rateAmount = ((RateCoupon) coupon).getRateAmount();
        }
    }
}
