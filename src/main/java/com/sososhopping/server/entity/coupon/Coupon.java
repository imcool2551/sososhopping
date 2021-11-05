package com.sososhopping.server.entity.coupon;

import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.store.Store;
import lombok.*;
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
public abstract class Coupon extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    protected Store store;

    @NotNull
    protected String storeName;

    @Column(name = "coupon_name")
    protected String couponName;

    @NotNull
    protected Integer stockQuantity;

    @NotNull
    @Column(unique = true, columnDefinition = "char")
    protected String couponCode;

    @NotNull
    protected Integer minimumOrderPrice;

    @NotNull
    protected LocalDateTime startDate;

    @NotNull
    protected LocalDateTime dueDate;

    @Column(name = "coupon_type", insertable = false, updatable = false)
    protected String couponType;

    public Coupon(
            String storeName,
            String couponName,
            Integer stockQuantity,
            String couponCode,
            Integer minimumOrderPrice,
            LocalDateTime startDate,
            LocalDateTime dueDate
    ) {
        this.storeName = storeName;
        this.couponName = couponName;
        this.stockQuantity = stockQuantity;
        this.couponCode = couponCode;
        this.minimumOrderPrice = minimumOrderPrice;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    abstract protected int getDiscountPrice();

    // 연관 관계 편의 메서드
    public void setStore(Store store) {
        this.store = store;
        this.store.getCoupons().add(this);
    }
}