package com.sososhopping.repository.coupon;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.sososhopping.entity.coupon.QCoupon.coupon;
import static com.sososhopping.entity.coupon.QUserCoupon.userCoupon;

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
        return userCoupon.coupon.expireDate.after(LocalDateTime.now());
    }

    private BooleanExpression userEq(User user) {
        return userCoupon.user.eq(user);
    }
}
