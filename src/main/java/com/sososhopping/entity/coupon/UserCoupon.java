package com.sososhopping.entity.coupon;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
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

    @Column(columnDefinition = "tinyint")
    private boolean used;

    private UserCoupon(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
        this.used = false;
    }

    public static UserCoupon createUserCoupon(User user, Coupon coupon, LocalDateTime at) {
        coupon.issueCoupon(at);
        return new UserCoupon(user, coupon);
    }

    public boolean belongsTo(Store store) {
        return getStore() == store;
    }

    public void use(LocalDateTime at) {
        if (used) {
            throw new BadRequestException("이미 사용한 쿠폰입니다");
        }

        if (coupon.isExpired(at)) {
            throw new BadRequestException("사용기한이 지났습니다");
        }
        used = true;
    }

    public void restore() {
        used = false;
    }

    public Store getStore() {
        return coupon.getStore();
    }
}