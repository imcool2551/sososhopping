package com.sososhopping.domain.store.dto.request;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.CouponType;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateCouponDto {

    @NotNull(message = "쿠폰 이름 필수")
    @NotBlank(message = "쿠폰 이름 필수")
    private String couponName;

    @NotNull(message = "쿠폰 할인금액 필수")
    @Min(value = 1, message = "쿠폰 할인금액 미달")
    private Integer amount;

    @NotNull(message = "쿠폰 종류 필수(FIX/RATE)")
    private String couponType;

    @NotNull(message = "쿠폰 수량 필수")
    @Min(value = 1, message = "쿠폰 최소 수량 미달")
    private Integer stockQuantity;

    @NotNull(message = "최소 주문 금액 필수")
    @Min(0)
    private Integer minimumOrderPrice;

    @NotNull(message = "발급 시작 시간 필수")
    private LocalDateTime issueStartDate;

    @NotNull(message = "발급 종료 시간 필수")
    private LocalDateTime issueDueDate;

    @NotNull(message = "유효 기간 필수")
    @Future(message = "유효 기간 오류")
    private LocalDateTime expireDate;

    public Coupon toEntity(Store store, CouponType couponType, String couponCode) {
        return Coupon.builder()
                .store(store)
                .couponName(couponName)
                .amount(amount)
                .couponType(couponType)
                .stockQuantity(stockQuantity)
                .couponCode(couponCode)
                .minimumOrderPrice(minimumOrderPrice)
                .issueStartDate(issueStartDate)
                .issueDueDate(issueDueDate)
                .expireDate(expireDate)
                .build();
    }
}
