package com.sososhopping.server.entity.orders;

import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @CreatedDate
    private LocalDateTime orderDate;

    @NotNull
    @Column(name = "orderer_name")
    private String ordererName;

    @Column(name = "orderer_phone", columnDefinition = "char")
    private String ordererPhone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    private LocalDateTime visitDate;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    //반정규화용
    private String storeName;

    private Integer deliveryCharge;

    private String deliveryStreetAddress;

    private String deliveryDetailedAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotNull
    private Integer orderPrice;

    private Integer usedPoint;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @NotNull
    private Integer finalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //List
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, mappedBy = "order")
    private Payment payment;

    @Builder
    public Order(User user, String ordererName, String ordererPhone, OrderType orderType, LocalDateTime visitDate, Store store, String storeName, Integer deliveryCharge, String deliveryStreetAddress, String deliveryDetailedAddress, PaymentType paymentType, Integer orderPrice, Integer usedPoint, Coupon coupon, Integer finalPrice, OrderStatus orderStatus, Payment payment) {
        this.user = user;
        this.ordererName = ordererName;
        this.ordererPhone = ordererPhone;
        this.orderType = orderType;
        this.visitDate = visitDate;
        this.store = store;
        this.storeName = storeName;
        this.deliveryCharge = deliveryCharge;
        this.deliveryStreetAddress = deliveryStreetAddress;
        this.deliveryDetailedAddress = deliveryDetailedAddress;
        this.paymentType = paymentType;
        this.orderPrice = orderPrice;
        this.usedPoint = usedPoint;
        this.coupon = coupon;
        this.finalPrice = finalPrice;
        this.orderStatus = orderStatus;
        this.payment = payment;
    }
}
