package com.sososhopping.entity.coupon;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_coupon_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private boolean used;

    public UserCoupon(User user, Coupon coupon, boolean used) {
        this.user = user;
        this.coupon = coupon;
        this.used = used;
    }

    public static UserCoupon buildUserCoupon(User user, Coupon coupon) {
        coupon.issueCoupon();
        return new UserCoupon(user, coupon, false);
    }

    // Business Logic
    public void use() {
        if (used == true) {
            throw new Api400Exception("이미 사용한 쿠폰입니다");
        }
        if (coupon.expireDate.isBefore(LocalDateTime.now())) {
            throw new Api400Exception("사용기한이 지났습니다");
        }
        used = true;
    }

    public void restore() {
        used = false;
    }
}