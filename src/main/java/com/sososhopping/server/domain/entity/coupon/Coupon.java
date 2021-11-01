package com.sososhopping.server.domain.entity.coupon;

import com.sososhopping.server.domain.entity.BaseTimeEntity;
import com.sososhopping.server.domain.entity.store.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "coupon_type", discriminatorType = DiscriminatorType.STRING, length = 20)
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Coupon extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private String storeName;

    @Column(name = "coupon_name")
    private String couponName;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    @Column(unique = true, columnDefinition = "char")
    private String couponCode;

    @NotNull
    private Integer minimumOrderPrice;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime dueDate;

    abstract protected int getDiscountPrice();

}