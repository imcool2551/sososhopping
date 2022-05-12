package com.sososhopping.domain.coupon.dto.response;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.Data;

@Data
public class UserCouponResponse {

    private Long id;
    private String nickname;
    private CouponResponse coupon;

    public UserCouponResponse(UserCoupon userCoupon, User user, Store store, Coupon coupon) {
        this.id = userCoupon.getId();
        this.nickname = user.getNickname();
        this.coupon = new CouponResponse(store, coupon);
    }
}
