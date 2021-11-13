package com.sososhopping.server.repository.coupon;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.server.entity.coupon.QCoupon;
import com.sososhopping.server.entity.coupon.QUserCoupon;
import com.sososhopping.server.entity.coupon.UserCoupon;
import com.sososhopping.server.entity.member.User;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.sososhopping.server.entity.coupon.QCoupon.*;
import static com.sososhopping.server.entity.coupon.QUserCoupon.*;

public class UserCouponRepositoryCustomImpl implements UserCouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserCouponRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserCoupon> findUsableCouponsByUser(User user) {
        return queryFactory
                .select(userCoupon)
                .from(userCoupon)
                .join(userCoupon.coupon, coupon).fetchJoin()
                .where(userEq(user), beforeExpiration(), notUsed())
                .fetch();
    }

    private BooleanExpression notUsed() {
        return userCoupon.used.isFalse();
    }

    private BooleanExpression beforeExpiration() {
        return userCoupon.coupon.expiryDate.after(LocalDateTime.now());
    }

    private BooleanExpression userEq(User user) {
        return userCoupon.user.eq(user);
    }
}
