package com.sososhopping.server.repository.coupon;

import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.store.Store;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {

    List<Coupon> findDownloadableByStore(Store store, LocalDateTime at);
}
