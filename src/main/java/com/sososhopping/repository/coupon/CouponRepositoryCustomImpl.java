package com.sososhopping.repository.coupon;

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
    public List<Coupon> findDownloadableByStore(Store store, LocalDateTime at) {
        return queryFactory
                .select(coupon)
                .from(coupon)
                .where(storeEq(store), inStock(), beingIssued(at))
                .fetch();
    }

    private BooleanExpression beingIssued(LocalDateTime at) {
        return coupon.issuedStartDate.before(at).and(coupon.issuedDueDate.after(at));
    }

    private BooleanExpression inStock() {
        return coupon.stockQuantity.gt(0);
    }

    private BooleanExpression storeEq(Store store) {
        return coupon.store.eq(store);
    }
}
