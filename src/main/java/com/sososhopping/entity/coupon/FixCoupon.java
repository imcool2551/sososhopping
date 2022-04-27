package com.sososhopping.entity.coupon;

import com.sososhopping.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;

//@Entity
@Getter
@DiscriminatorValue("FIX")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixCoupon extends Coupon {

    private Integer fixAmount;

    @Override
    public int getDiscountPrice(int orderPrice) {
        return fixAmount;
    }

    public FixCoupon(Store store, StoreCouponRequestDto dto, String couponCode) {
        super(store, dto, couponCode);
        this.fixAmount = dto.getFixAmount();
    }
}