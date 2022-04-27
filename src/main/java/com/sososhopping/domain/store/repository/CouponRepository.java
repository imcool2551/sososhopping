package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.repository.coupon.CouponRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

    //발행 예정 쿠폰 read

    //발행중인 쿠폰 read

    //code로 쿠폰 탐색
    @EntityGraph(attributePaths = {"store"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Coupon> findByCouponCode(String couponCode);

}
