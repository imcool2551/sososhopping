package com.sososhopping.server.entity.coupon;

import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
}