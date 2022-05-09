package com.sososhopping.entity.coupon;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String couponName;

    private int amount;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    private int stockQuantity;

    @Column(columnDefinition = "char", length = 10)
    private String couponCode;

    private int minimumOrderPrice;

    private LocalDateTime issueStartDate;

    private LocalDateTime issueDueDate;

    private LocalDateTime expireDate;


    @Builder
    public Coupon(Store store, String couponName, int amount, CouponType couponType,
                  int stockQuantity, String couponCode, int minimumOrderPrice,
                  LocalDateTime issueStartDate, LocalDateTime issueDueDate, LocalDateTime expireDate) {

        this.store = store;
        this.couponName = couponName;
        this.amount = amount;
        this.couponType = couponType;
        this.stockQuantity = stockQuantity;
        this.couponCode = couponCode;
        this.minimumOrderPrice = minimumOrderPrice;
        this.issueStartDate = issueStartDate;
        this.issueDueDate = issueDueDate;
        this.expireDate = expireDate;
    }

    public int calculateDiscountPrice(int orderPrice) {
        return couponType.calculateDiscountPrice(orderPrice, amount);
    }


    public boolean belongsTo(Store store) {
        return this.store == store;
    }

    public void issueCoupon(LocalDateTime at) {
        if (stockQuantity <= 0) {
            throw new BadRequestException("쿠폰 재고가 없습니다");
        }
        if (!isActive(at)) {
            throw new BadRequestException("쿠폰 발급기간이 아닙니다");
        }
        stockQuantity--;
    }

    private boolean isActive(LocalDateTime at) {
        return at.isAfter(issueStartDate) && at.isBefore(issueDueDate);
    }

    public boolean isExpired(LocalDateTime at) {
        return expireDate.isBefore(at);
    }

    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    public boolean usable(int orderPrice) {
        return minimumOrderPrice <= orderPrice;
    }
}