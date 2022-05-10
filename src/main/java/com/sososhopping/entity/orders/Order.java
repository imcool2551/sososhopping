package com.sososhopping.entity.orders;

import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.owner.Owner;
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

    @Embedded
    private OrdererInfo ordererInfo;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private LocalDateTime visitDate;

    @Embedded
    private DeliveryInfo deliveryInfo;

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
    public Order(User user, String ordererName, String ordererPhone,
                 OrderType orderType, LocalDateTime visitDate, Store store,
                 Integer deliveryCharge, String deliveryStreetAddress, String deliveryDetailedAddress,
                 PaymentType paymentType, Integer orderPrice, Integer usedPoint,
                 Coupon coupon, Integer finalPrice, OrderStatus orderStatus) {

        this.user = user;
        this.ordererInfo = new OrdererInfo(ordererName, ordererPhone);
        this.orderType = orderType;
        this.visitDate = visitDate;
        this.store = store;
        this.deliveryInfo = new DeliveryInfo(deliveryCharge, deliveryStreetAddress, deliveryDetailedAddress);
        this.paymentType = paymentType;
        this.orderPrice = orderPrice;
        this.usedPoint = usedPoint;
        this.coupon = coupon;
        this.finalPrice = finalPrice;
        this.orderStatus = orderStatus;
    }

    public void cancel(User user, UserPoint userPoint, UserCoupon userCoupon) {
        validateUser(user);
        orderStatus = orderStatus.toCancel();
        restorePointAndUserCoupon(userPoint, userCoupon);
    }

    public void confirm(User user, UserPoint userPoint) {
        validateUser(user);
        orderStatus = orderStatus.toDone();
        if (store.hasPointPolicy()) {
            int plusPoint = (int) (finalPrice * store.getSaveRate().doubleValue() / 100);
            userPoint.updatePoint(plusPoint);
        }
    }

    public void reject(Owner owner, UserPoint userPoint, UserCoupon userCoupon) {
        validateOwner(owner);
        orderStatus = orderStatus.toReject();
        restorePointAndUserCoupon(userPoint, userCoupon);
    }

    public void ready(Owner owner) {
        validateOwner(owner);
        orderStatus = orderStatus.toReady();
    }

    public void approve(Owner owner) {
        validateOwner(owner);
        orderStatus = orderStatus.toApprove();
    }

    private void restorePointAndUserCoupon(UserPoint userPoint, UserCoupon userCoupon) {
        if (userPoint != null) {
            userPoint.updatePoint(usedPoint);
        }
        if (userCoupon != null) {
            userCoupon.restore();
        }
    }

    private void validateUser(User user) {
        if (!belongsTo(user)) {
            throw new ForbiddenException("order does not belong to user");
        }
    }

    private void validateOwner(Owner owner) {
        if (!belongsTo(owner)) {
            throw new ForbiddenException("order does not belong to store");
        }
    }

    private boolean belongsTo(Owner owner) {
        return store.getOwner() == owner;
    }

    public boolean belongsTo(User user) {
        return this.user == user;
    }

    public String getOrdererName() {
        return ordererInfo.getOrdererName();
    }

    public String getOrdererPhone() {
        return ordererInfo.getOrdererPhone();
    }

    public Integer getDeliveryCharge() {
        return deliveryInfo.getDeliveryCharge();
    }

    public String getDeliveryStreetAddress() {
        return deliveryInfo.getDeliveryStreetAddress();
    }

    public String getDeliveryDetailedAddress() {
        return deliveryInfo.getDeliveryDetailedAddress();
    }
}
