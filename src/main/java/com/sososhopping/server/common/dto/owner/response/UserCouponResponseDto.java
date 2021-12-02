package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.coupon.FixCoupon;
import com.sososhopping.server.entity.coupon.RateCoupon;
import com.sososhopping.server.entity.member.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCouponResponseDto {
    private String userNickname;
    private String couponCode;
    private String storeName;
    private String couponType;
    private Integer fixAmount;
    private BigDecimal rateAmount;
    private Integer minimumOrderPrice;
    private String expiryDate;

    public UserCouponResponseDto(User user, Coupon coupon) {
        this.userNickname = user.getNickname();
        this.couponCode = coupon.getCouponCode();
        this.storeName = coupon.getStoreName();
        this.couponType = coupon.getCouponType();
        this.minimumOrderPrice = coupon.getMinimumOrderPrice();
        this.expiryDate = coupon.getExpiryDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        if (coupon instanceof RateCoupon) {
            this.rateAmount = ((RateCoupon) coupon).getRateAmount();
        } else if (coupon instanceof FixCoupon) {
            this.fixAmount = ((FixCoupon) coupon).getFixAmount();
        }
    }
}
