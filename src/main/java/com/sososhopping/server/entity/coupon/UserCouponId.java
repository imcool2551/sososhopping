package com.sososhopping.server.entity.coupon;

import com.sososhopping.server.entity.member.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponId implements Serializable {
    private Long user;
    private Long coupon;
}
