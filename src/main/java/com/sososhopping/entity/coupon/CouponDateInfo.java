package com.sososhopping.entity.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDateInfo {

    private LocalDateTime issueStartDate;

    private LocalDateTime issueDueDate;

    private LocalDateTime expireDate;

    public CouponDateInfo(LocalDateTime issueStartDate, LocalDateTime issueDueDate, LocalDateTime expireDate) {
        this.issueStartDate = issueStartDate;
        this.issueDueDate = issueDueDate;
        this.expireDate = expireDate;
    }

    public boolean isActive(LocalDateTime at) {
        return at.isAfter(issueStartDate) && at.isBefore(issueDueDate);
    }

    public boolean isExpired(LocalDateTime at) {
        return expireDate.isBefore(at);
    }
}
