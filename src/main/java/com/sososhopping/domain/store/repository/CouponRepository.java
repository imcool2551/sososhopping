package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.repository.coupon.CouponRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

    //code로 쿠폰 탐색
    @EntityGraph(attributePaths = {"store"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Coupon> findByCouponCode(String couponCode);

}
