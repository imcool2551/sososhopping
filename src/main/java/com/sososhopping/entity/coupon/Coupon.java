package com.sososhopping.entity.coupon;

import com.sososhopping.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    protected Store store;

    protected String couponName;

    protected int stockQuantity;

    protected String couponCode;

    protected int minimumOrderPrice;

    protected LocalDateTime issueStartDate;

    protected LocalDateTime issueDueDate;

    protected LocalDateTime expireDate;

    protected String couponType;

    public Coupon(String couponName, Integer stockQuantity, String couponCode, Integer minimumOrderPrice, LocalDateTime startDate, LocalDateTime dueDate) {
        this.couponName = couponName;
        this.stockQuantity = stockQuantity;
        this.couponCode = couponCode;
        this.minimumOrderPrice = minimumOrderPrice;
        this.issueStartDate = startDate;
        this.issueDueDate = dueDate;
    }

    abstract public int getDiscountPrice(int orderPrice);


    public Coupon(Store store, StoreCouponRequestDto dto, String couponCode) {
        this.store = store;
        this.couponName = dto.getCouponName();
        this.stockQuantity = dto.getStockQuantity();
        this.minimumOrderPrice = dto.getMinimumOrderPrice();
        this.issueStartDate = LocalDateTime.parse(dto.getIssuedStartDate() + " 00:00:00",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.issueDueDate = LocalDateTime.parse(dto.getIssuedDueDate() + " 23:59:59",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.expireDate = LocalDateTime.parse(dto.getExpiryDate() + " 23:59:59",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.couponCode = couponCode;
    }

    public void update(StoreCouponRequestDto dto) {
        this.issueDueDate = LocalDateTime.parse(dto.getIssuedDueDate() + " 23:59:59",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public Coupon issueCoupon() {
        if (!hasStock()) {
            throw new Api400Exception("쿠폰 재고가 없습니다");
        }
        if (!isBeingIssuedAt(LocalDateTime.now())) {
            throw new Api400Exception("쿠폰 발급기간이 아닙니다");
        }
        stockQuantity--;
        return this;
    }

    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    public boolean belongsTo(Store store) {
        return this.store == store;
    }

    public boolean minimumPriceGreaterThan(Integer orderPrice) {
        return minimumOrderPrice > orderPrice;
    }

    private boolean hasStock() {
        return stockQuantity > 0;
    }

    private boolean isBeingIssuedAt(LocalDateTime at) {
        return at.isAfter(issueStartDate) && at.isBefore(issueDueDate);
    }
}