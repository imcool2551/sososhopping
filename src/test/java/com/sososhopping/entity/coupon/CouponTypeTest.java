package com.sososhopping.entity.coupon;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static com.sososhopping.entity.coupon.CouponType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTypeTest {

    @Test
    void of() {
        assertThat(CouponType.of("RATE")).isSameAs(RATE);
        assertThat(CouponType.of("FIX")).isSameAs(FIX);
        assertThatThrownBy(() -> CouponType.of("ABCD"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void calculateDiscountPrice() {
        assertThat(RATE.calculateDiscountPrice(12350,10)).isEqualTo(1235);
        assertThat(FIX.calculateDiscountPrice(12350,1000)).isEqualTo(1000);
    }
}
