package com.sososhopping.domain.coupon.repository;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, UserCouponRepositoryCustom {

    @Override
    @EntityGraph(attributePaths = {"coupon"}, type = FETCH)
    Optional<UserCoupon> findById(Long id);

    Optional<UserCoupon> findByUserAndCoupon(User user, Coupon coupon);

    boolean existsByUserAndCoupon(User user, Coupon coupon);
}
