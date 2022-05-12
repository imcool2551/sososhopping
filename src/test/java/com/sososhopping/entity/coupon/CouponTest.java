package com.sososhopping.entity.coupon;

import com.sososhopping.common.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @Test
    void issueCoupon_WhenNoStock_ThrowsBadRequestException() {
        Coupon coupon = Coupon.builder()
                .stockQuantity(0)
                .build();

        assertThatThrownBy(() -> coupon.issueCoupon(now()))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void issueCoupon_WhenNotActive_ThrowsBadRequestException() {
        Coupon coupon = Coupon.builder()
                .issueStartDate(now().plusDays(1))
                .issueDueDate(now().plusDays(2))
                .stockQuantity(100)
                .build();

        assertThatThrownBy(() -> coupon.issueCoupon(now()))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void issueCoupon() {
        Coupon coupon = Coupon.builder()
                .issueStartDate(now().minusDays(1))
                .issueDueDate(now().plusDays(2))
                .stockQuantity(100)
                .build();

        coupon.issueCoupon(now());

        assertThat(coupon.getStockQuantity()).isEqualTo(99);
    }
}
