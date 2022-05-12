package com.sososhopping.domain.coupon.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.store.Store;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.sososhopping.entity.coupon.QCoupon.coupon;

public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CouponRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Coupon> findActiveCoupons(Store store, LocalDateTime dateTime) {
        return queryFactory
                .select(coupon)
                .from(coupon)
                .where(storeEq(store), inStock(), beingIssued(dateTime))
                .fetch();
    }

    @Override
    public List<Coupon> findScheduledCoupons(Store store, LocalDateTime dateTime) {
        return queryFactory
                .select(coupon)
                .from(coupon)
                .where(storeEq(store), scheduled(dateTime))
                .fetch();
    }

    private BooleanExpression storeEq(Store store) {
        return coupon.store.eq(store);
    }

    private BooleanExpression inStock() {
        return coupon.stockQuantity.gt(0);
    }

    private BooleanExpression beingIssued(LocalDateTime at) {
        return coupon.issueStartDate.before(at)
                .and(coupon.issueDueDate.after(at))
                .and(coupon.expireDate.after(at));
    }

    private BooleanExpression scheduled(LocalDateTime at) {
        return coupon.issueStartDate.after(at);
    }
}
