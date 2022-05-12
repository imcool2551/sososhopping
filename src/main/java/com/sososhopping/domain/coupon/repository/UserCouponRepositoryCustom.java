package com.sososhopping.domain.coupon.repository;

import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserCouponRepositoryCustom {

    List<UserCoupon> findActiveCouponsByUserAt(User user, LocalDateTime dateTime);
}
