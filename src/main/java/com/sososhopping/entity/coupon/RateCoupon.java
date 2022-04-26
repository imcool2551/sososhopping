package com.sososhopping.entity.coupon;

import com.sososhopping.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("RATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RateCoupon extends Coupon {

    private BigDecimal rateAmount;

    @Builder
    public RateCoupon(
            String couponName,
            Integer stockQuantity,
            String couponCode,
            Integer minimumOrderPrice,
            LocalDateTime startDate,
            LocalDateTime dueDate,
            BigDecimal rateAmount
    ) {
        super(couponName, stockQuantity, couponCode, minimumOrderPrice, startDate, dueDate);
        this.rateAmount = rateAmount;
    }

    @Override
    public int getDiscountPrice(int orderPrice) {
        return (int)(orderPrice * rateAmount.doubleValue() / 100);
    }

    public RateCoupon(Store store, StoreCouponRequestDto dto, String couponCode) {
        super(store, dto, couponCode);
        this.rateAmount = dto.getRateAmount();
    }
}