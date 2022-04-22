package com.sososhopping.entity.coupon;

import com.sososhopping.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("FIX")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixCoupon extends Coupon {

    private Integer fixAmount;

    @Override
    public int getDiscountPrice(int orderPrice) {
        return fixAmount;
    }

    @Builder
    public FixCoupon(
            String storeName,
            String couponName,
            Integer stockQuantity,
            String couponCode,
            Integer minimumOrderPrice,
            LocalDateTime startDate,
            LocalDateTime dueDate,
            Integer fixAmount
    ) {
        super(storeName, couponName, stockQuantity, couponCode, minimumOrderPrice, startDate, dueDate);
        this.fixAmount = fixAmount;
    }

    public FixCoupon(Store store, StoreCouponRequestDto dto, String couponCode) {
        super(store, dto, couponCode);
        this.fixAmount = dto.getFixAmount();
    }
}