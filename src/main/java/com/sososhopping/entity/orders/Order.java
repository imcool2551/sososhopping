package com.sososhopping.entity.orders;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String ordererName;

    @Column(columnDefinition = "char", length = 11)
    private String ordererPhone;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private LocalDateTime visitDate;

    private Integer deliveryCharge;

    private String deliveryStreetAddress;

    private String deliveryDetailedAddress;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private int orderPrice;

    private Integer usedPoint;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private int finalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    @Builder
    public Order(User user, String ordererName, String ordererPhone, OrderType orderType, LocalDateTime visitDate, Store store, Integer deliveryCharge, String deliveryStreetAddress, String deliveryDetailedAddress, PaymentType paymentType, Integer orderPrice, Integer usedPoint, Coupon coupon, Integer finalPrice, OrderStatus orderStatus) {
        this.user = user;
        this.ordererName = ordererName;
        this.ordererPhone = ordererPhone;
        this.orderType = orderType;
        this.visitDate = visitDate;
        this.store = store;
        this.deliveryCharge = deliveryCharge;
        this.deliveryStreetAddress = deliveryStreetAddress;
        this.deliveryDetailedAddress = deliveryDetailedAddress;
        this.paymentType = paymentType;
        this.orderPrice = orderPrice;
        this.usedPoint = usedPoint;
        this.coupon = coupon;
        this.finalPrice = finalPrice;
        this.orderStatus = orderStatus;
    }

    public boolean canBeCancelledByUser() {
        return orderStatus == OrderStatus.PENDING;
    }

    public void cancel(UserPoint userPoint, UserCoupon userCoupon) {
        orderStatus = OrderStatus.CANCEL;
        if (userPoint != null) {
            userPoint.restorePoint(this);
        }
        if (userCoupon != null) {
            userCoupon.restore();
        }
    }

    public boolean canBeConfirmedByUser() {
        return orderStatus == OrderStatus.READY;
    }

    public void confirm(UserPoint userPoint) {
        orderStatus = OrderStatus.DONE;
        if (userPoint != null) {
            userPoint.savePoint(this);
        }
    }

    public void approve() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new Api400Exception("잘못된 요청입니다");
        }
        orderStatus = OrderStatus.APPROVE;
    }

    public void reject(UserPoint userPoint, UserCoupon userCoupon) {
        if (orderStatus == OrderStatus.DONE || orderStatus == OrderStatus.CANCEL) {
            throw new Api400Exception("잘못된 요청입니다");
        }

        orderStatus = OrderStatus.REJECT;
        if (userPoint != null) {
            userPoint.restorePoint(this);
        }
        if (userCoupon != null) {
            userCoupon.restore();
        }
    }

    public void ready() {
        if (orderStatus != OrderStatus.APPROVE) {
            throw new Api400Exception("잚못된 요청입니다");
        }
        orderStatus = OrderStatus.READY;
    }

}
