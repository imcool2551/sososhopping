package com.sososhopping.common.dto.owner.response;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.member.User;
import lombok.*;

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
