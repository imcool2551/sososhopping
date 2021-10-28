package com.sososhopping.server.entity.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;
import sosohagae.sososhop.entity.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "COUPON_TYPE")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Coupon extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String couponName;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    @Column(unique = true)
    private String couponCode;

    @NotNull
    private Integer minimumOrderPrice;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime dueDate;

    abstract protected int getDiscountPrice();
}
