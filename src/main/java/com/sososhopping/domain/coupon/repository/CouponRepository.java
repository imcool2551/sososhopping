package com.sososhopping.domain.coupon.repository;

import com.sososhopping.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

    Optional<Coupon> findByCouponCode(String couponCode);

}
