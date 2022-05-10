package com.sososhopping.entity.coupon;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserCouponTest {

    private static User user;
    private static Store storeA;
    private static Store storeB;

    @BeforeEach
    void setUp() {
        user = User.builder().build();
        storeA = Store.builder().build();
        storeB = Store.builder().build();
    }

    @Test
    void use_WhenNotEnoughOrderPrice_ThrowsBadRequestException() {
        Coupon coupon = Coupon.builder()
                .store(storeA)
                .minimumOrderPrice(10000)
                .stockQuantity(100)
                .issueStartDate(now().minusDays(1))
                .issueDueDate(now().plusDays(30))
                .expireDate(now().plusDays(30))
                .build();
        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon, now());

        assertThatThrownBy(() -> userCoupon.use(storeA, 5000, now()))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void use_WhenStoreMisMatch_ThrowsBadRequestException() {
        Coupon coupon = Coupon.builder()
                .store(storeA)
                .stockQuantity(100)
                .issueStartDate(now().minusDays(1))
                .issueDueDate(now().plusDays(30))
                .build();
        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon, now());

        assertThatThrownBy(() -> userCoupon.use(storeB, now()))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void use_WhenExpired_ThrowsBadRequestException() {
        Coupon coupon = Coupon.builder()
                .store(storeA)
                .stockQuantity(100)
                .issueStartDate(now().minusDays(10))
                .issueDueDate(now().plusDays(30))
                .expireDate(now().plusDays(40))
                .build();
        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon, now());

        assertThatThrownBy(() -> userCoupon.use(storeA, now().plusDays(50)))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void use_AlreadyUsed_ThrowsBadRequestException() {
        Coupon coupon = Coupon.builder()
                .store(storeA)
                .stockQuantity(100)
                .issueStartDate(now().minusDays(10))
                .issueDueDate(now().plusDays(30))
                .expireDate(now().plusDays(40))
                .build();
        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon, now());

        userCoupon.use(storeA, now());
        assertThatThrownBy(() -> userCoupon.use(storeA, now()))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void use() {
        Coupon coupon = Coupon.builder()
                .store(storeA)
                .stockQuantity(100)
                .issueStartDate(now().minusDays(10))
                .issueDueDate(now().plusDays(30))
                .expireDate(now().plusDays(40))
                .build();
        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon, now());

        userCoupon.use(storeA, now());

        assertThat(userCoupon.isUsed()).isTrue();
    }
}
