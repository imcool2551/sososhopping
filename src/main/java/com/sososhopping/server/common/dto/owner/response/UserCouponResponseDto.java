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
    private StoreCouponResponseDto coupon;

    public UserCouponResponseDto(User user, Coupon coupon, Long storeId) {
        this.userNickname = user.getNickname();
        this.coupon = new StoreCouponResponseDto(coupon, storeId);
    }
}
