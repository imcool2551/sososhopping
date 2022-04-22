package com.sososhopping.repository.coupon;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.coupon.UserCouponId;
import com.sososhopping.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, UserCouponId>, UserCouponRepositoryCustom {
    @EntityGraph(attributePaths = {"coupon"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserCoupon> findByUserAndCoupon(User user, Coupon coupon);

    boolean existsByUserAndCoupon(User user, Coupon coupon);
}
