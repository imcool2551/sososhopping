package com.sososhopping.domain.coupon.repository;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.store.Store;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {

    List<Coupon> findActiveCoupons(Store store, LocalDateTime dateTime);

    List<Coupon> findScheduledCoupons(Store store, LocalDateTime dateTime);
}
