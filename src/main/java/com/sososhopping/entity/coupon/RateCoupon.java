package com.sososhopping.entity.coupon;

import com.sososhopping.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import java.math.BigDecimal;

//@Entity
@Getter
@DiscriminatorValue("RATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RateCoupon extends Coupon {

    private BigDecimal rateAmount;

    @Override
    public int getDiscountPrice(int orderPrice) {
        return (int)(orderPrice * rateAmount.doubleValue() / 100);
    }

    public RateCoupon(Store store, StoreCouponRequestDto dto, String couponCode) {
        super(store, dto, couponCode);
        this.rateAmount = dto.getRateAmount();
    }
}