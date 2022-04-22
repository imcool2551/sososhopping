package com.sososhopping.common.dto.owner.response;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.FixCoupon;
import com.sososhopping.entity.coupon.RateCoupon;
import lombok.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreCouponResponseDto {
    private Long storeId;
    private Long id;
    private String storeName;
    private String couponName;
    private Integer stockQuantity;
    private String couponCode;
    private Integer minimumOrderPrice;
    private String issuedStartDate;
    private String issuedDueDate;
    private String expiryDate;
    private String couponType;
    private BigDecimal rateAmount;
    private Integer fixAmount;

    public StoreCouponResponseDto(Coupon coupon, Long storeId) {
        this.storeId = storeId;
        this.id = coupon.getId();
        this.storeName = coupon.getStoreName();
        this.couponName = coupon.getCouponName();
        this.stockQuantity = coupon.getStockQuantity();
        this.couponCode = coupon.getCouponCode();
        this.minimumOrderPrice = coupon.getMinimumOrderPrice();
        this.issuedStartDate = coupon.getIssuedStartDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.issuedDueDate = coupon.getIssuedDueDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.expiryDate = coupon.getExpiryDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.couponType = coupon.getCouponType();

        if (coupon instanceof FixCoupon) {
            this.fixAmount = ((FixCoupon) coupon).getFixAmount();
        } else if (coupon instanceof RateCoupon) {
            this.rateAmount = ((RateCoupon) coupon).getRateAmount();
        }
    }
}
