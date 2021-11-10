package com.sososhopping.server.repository.coupon;

import com.sososhopping.server.entity.coupon.UserCoupon;
import com.sososhopping.server.entity.coupon.UserCouponId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, UserCouponId> {

}
