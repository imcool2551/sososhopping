package com.sososhopping.common.dto.user.response.store;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.FixCoupon;
import com.sososhopping.entity.coupon.RateCoupon;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter
public class CouponDto {

    private Long couponId;
    private String storeName;
    private String couponName;
    private Integer stockQuantity;
    private String couponCode;
    private Integer minimumOrderPrice;
    private String issuedStartDate;
    private String issuedDueDate;
    private String expiryDate;
    private String couponType;
    private Integer fixAmount;
    private BigDecimal rateAmount;

    public CouponDto(Coupon coupon) {
        couponId = coupon.getId();
        couponName = coupon.getCouponName();
        stockQuantity = coupon.getStockQuantity();
        couponCode = coupon.getCouponCode();
        minimumOrderPrice = coupon.getMinimumOrderPrice();
        issuedStartDate = coupon.getIssueStartDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        issuedDueDate = coupon.getIssueDueDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        couponType = coupon.getCouponType();
        if (coupon instanceof FixCoupon) {
            fixAmount = ((FixCoupon) coupon).getFixAmount();
        } else if (coupon instanceof RateCoupon) {
            rateAmount = ((RateCoupon) coupon).getRateAmount();
        }
    }
}