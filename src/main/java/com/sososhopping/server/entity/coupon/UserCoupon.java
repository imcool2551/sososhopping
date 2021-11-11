package com.sososhopping.server.entity.coupon;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(UserCouponId.class)
public class UserCoupon extends BaseTimeEntity {

    @Id
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean used;

    public static UserCoupon buildUserCoupon(User user, Coupon coupon) {
        coupon.issueCoupon();
        return new UserCoupon(user, coupon, false);
    }

    // Business Logic
    public void useCoupon() {
        if (used == true) {
            throw new Api400Exception("이미 사용한 쿠폰입니다");
        }
        if (coupon.expiryDate.isBefore(LocalDateTime.now())) {
            throw new Api400Exception("사용기한이 지났습니다");
        }
        used = true;
    }
}