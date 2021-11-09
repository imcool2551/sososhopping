package com.sososhopping.server.entity.coupon;

import com.sososhopping.server.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.server.entity.store.Store;
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
            String storeName,
            String couponName,
            Integer stockQuantity,
            String couponCode,
            Integer minimumOrderPrice,
            LocalDateTime startDate,
            LocalDateTime dueDate,
            BigDecimal rateAmount
    ) {
        super(storeName, couponName, stockQuantity, couponCode, minimumOrderPrice, startDate, dueDate);
        this.rateAmount = rateAmount;
    }

    @Override
    protected int getDiscountPrice() {
        return 0;
    }

    public RateCoupon(Store store, StoreCouponRequestDto dto, String couponCode) {
        super(store, dto, couponCode);
        this.rateAmount = dto.getRateAmount();
    }
}