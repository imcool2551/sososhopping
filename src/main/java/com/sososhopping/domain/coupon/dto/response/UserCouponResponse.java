package com.sososhopping.domain.coupon.dto.response;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.store.Store;
import lombok.Data;

@Data
public class UserCouponResponse {

    private String nickname;
    private CouponResponse coupon;

    public UserCouponResponse(String nickname, Store store, Coupon coupon) {
        this.nickname = nickname;
        this.coupon = new CouponResponse(store, coupon);
    }
}
