package com.sososhopping.server.repository.coupon;

import com.sososhopping.server.entity.coupon.UserCoupon;
import com.sososhopping.server.entity.member.User;

import java.util.List;

public interface UserCouponRepositoryCustom {

    List<UserCoupon> findUsableCouponsByUser(User user);
}
