package com.sososhopping.domain.coupon.dto.response;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.CouponType;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreCouponResponse {

    private Long id;
    private Long storeId;
    private String couponName;
    private int amount;
    private CouponType couponType;
    private int stockQuantity;
    private String couponCode;
    private int minimumOrderPrice;
    private LocalDateTime issueStartDate;
    private LocalDateTime issueDueDate;
    private LocalDateTime expireDate;


    public StoreCouponResponse(Store store, Coupon coupon) {
        this.id = coupon.getId();
        this.storeId = store.getId();
        this.couponName = coupon.getCouponName();
        this.amount = coupon.getAmount();
        this.couponType = coupon.getCouponType();
        this.stockQuantity = coupon.getStockQuantity();
        this.couponCode = coupon.getCouponCode();
        this.minimumOrderPrice = coupon.getMinimumOrderPrice();
        this.issueStartDate = coupon.getIssueStartDate();
        this.issueDueDate = coupon.getIssueDueDate();
        this.expireDate = coupon.getExpireDate();
    }
}
