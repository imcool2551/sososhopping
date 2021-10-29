package com.sososhopping.server.domain.entity.coupon;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponId implements Serializable {
    private String user;
    private String coupon;
}
