package com.sososhopping.domain.coupon.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.sososhopping.entity.coupon.QCoupon.coupon;
import static com.sososhopping.entity.coupon.QUserCoupon.userCoupon;
import static com.sososhopping.entity.store.QStore.store;

public class UserCouponRepositoryCustomImpl implements UserCouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserCouponRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserCoupon> findActiveCouponsByUserAt(User user, LocalDateTime at) {
        return queryFactory
                .select(userCoupon)
                .from(userCoupon)
                .join(userCoupon.coupon, coupon).fetchJoin()
                .join(coupon.store, store).fetchJoin()
                .where(userEq(user), isActiveAt(at), notUsed())
                .fetch();
    }

    private BooleanExpression userEq(User user) {
        return userCoupon.user.eq(user);
    }

    private BooleanExpression isActiveAt(LocalDateTime at) {
        return userCoupon.coupon.couponDateInfo.expireDate.after(at);
    }

    private BooleanExpression notUsed() {
        return userCoupon.used.isFalse();
    }
}
