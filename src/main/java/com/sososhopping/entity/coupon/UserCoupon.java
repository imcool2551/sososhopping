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
    private boolean used = false;

    private UserCoupon(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
    }

    public static UserCoupon createUserCoupon(User user, Coupon coupon, LocalDateTime at) {
        coupon.issueCoupon(at);
        return new UserCoupon(user, coupon);
    }

    public void use(Store store, LocalDateTime at) {
        validateUsability(store, at);
        used = true;
    }

    public void use(Store store, int orderPrice, LocalDateTime at) {
        validateUsability(store, at);
        if (!coupon.usable(orderPrice)) {
            throw new BadRequestException("최소 주문 금액 미달입니다");
        }
        used = true;
    }

    private void validateUsability(Store store, LocalDateTime at) {
        if (!belongsTo(store)) {
            throw new BadRequestException("해당 점포의 쿠폰이 아닙니다");
        }
        if (used) {
            throw new BadRequestException("이미 사용한 쿠폰입니다");
        }
        if (coupon.isExpired(at)) {
            throw new BadRequestException("사용기한이 지났습니다");
        }
    }

    private boolean belongsTo(Store store) {
        return getStore() == store;
    }

    public void restore() {
        used = false;
    }

    public Store getStore() {
        return coupon.getStore();
    }
}