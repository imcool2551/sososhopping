package com.sososhopping.domain.coupon.dto.response;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class StoreCouponsResponse {

    private List<CouponResponse> activeCoupons;
    private List<CouponResponse> scheduledCoupons;

    public StoreCouponsResponse(Store store, List<Coupon> activeCoupons, List<Coupon> scheduledCoupons) {
        this.activeCoupons = activeCoupons.stream()
                .map(coupon -> new CouponResponse(store, coupon))
                .collect(Collectors.toList());

        this.scheduledCoupons = scheduledCoupons.stream()
                .map(coupon -> new CouponResponse(store, coupon))
                .collect(Collectors.toList());
    }
}
