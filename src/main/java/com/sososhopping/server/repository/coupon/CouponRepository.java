package com.sososhopping.server.repository.coupon;

import com.sososhopping.server.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
