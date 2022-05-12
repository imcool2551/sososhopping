package com.sososhopping.entity.orders;

import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.sososhopping.entity.orders.OrderStatus.*;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void cancel_WhenUserNotMatch_ThrowsForbiddenException() {
        Store store = Store.builder().build();
        User userA = User.builder().build();
        User userB = User.builder().build();
        Order order = Order.builder()
                .store(store)
                .user(userA)
                .orderStatus(PENDING)
                .orderPrice(10000)
                .finalPrice(10000)
                .build();

        assertThatThrownBy(() -> order.cancel(userB, null, null))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void cancel_WhenUserPointExists_RestoresUserPoint() {
        Store store = Store.builder().build();
        User user = User.builder().build();
        UserPoint userPoint = new UserPoint(user, store, 20000);
        Order order = Order.builder()
                .store(store)
                .user(user)
                .orderStatus(PENDING)
                .orderPrice(10000)
                .usedPoint(500)
                .finalPrice(9500)
                .build();

        order.cancel(user, userPoint, null);

        assertThat(userPoint.getPoint()).isEqualTo(20000 + 500);
        assertThat(userPoint.getUserPointLogs()).hasSize(1);
        assertThat(order.getOrderStatus()).isEqualTo(CANCEL);
    }

    @Test
    void cancel_WhenUserCouponExists_RestoresUserCoupon() {
        Store store = Store.builder().build();
        Coupon coupon = Coupon.builder()
                .stockQuantity(100)
                .issueStartDate(now().minusDays(1))
                .issueDueDate(now().plusDays(1))
                .expireDate(now().plusDays(10))
                .store(store)
                .build();
        User user = User.builder().build();
        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon, now());
        Order order = Order.builder()
                .store(store)
                .user(user)
                .orderStatus(PENDING)
                .coupon(coupon)
                .orderPrice(10000)
                .usedPoint(500)
                .finalPrice(9500)
                .build();

        userCoupon.use(store, now());
        order.cancel(user, null, userCoupon);

        assertThat(userCoupon.isUsed()).isFalse();
        assertThat(order.getOrderStatus()).isEqualTo(CANCEL);
    }

    @Test
    void confirm() {
        Store store = Store.builder()
                .pointPolicyStatus(true)
                .saveRate(BigDecimal.valueOf(10))
                .build();
        User user = User.builder().build();
        UserPoint userPoint = new UserPoint(user, store, 20000);
        Order order = Order.builder()
                .store(store)
                .user(user)
                .orderStatus(READY)
                .orderPrice(10000)
                .usedPoint(500)
                .finalPrice(9500)
                .build();

        order.confirm(user, userPoint);

        assertThat(userPoint.getPoint()).isEqualTo(20000 + 950);
        assertThat(userPoint.getUserPointLogs()).hasSize(1);
        assertThat(order.getOrderStatus()).isEqualTo(DONE);
    }
}
