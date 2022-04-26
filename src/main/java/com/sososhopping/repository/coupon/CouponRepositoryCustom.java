package com.sososhopping.repository.coupon;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.store.Store;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {

    List<Coupon> findDownloadableByStore(Store store, LocalDateTime at);
}
