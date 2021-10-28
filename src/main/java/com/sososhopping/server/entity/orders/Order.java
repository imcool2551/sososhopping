package com.sososhopping.server.entity.orders;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;
import sosohagae.sososhop.entity.coupon.Coupon;
import sosohagae.sososhop.entity.member.users.User;
import sosohagae.sososhop.entity.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue
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
    private String ordererName;

    private String ordererPhone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private LocalDateTime visitDate;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer deliveryCharge;

    private String deliveryStreetAddress;

    private String deliveryDetailedAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotNull
    private Integer orderPrice;

    private Integer usedPoint;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @NotNull
    private Integer finalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
