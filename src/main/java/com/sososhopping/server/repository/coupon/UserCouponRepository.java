package com.sososhopping.server.repository.coupon;

import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.coupon.UserCoupon;
import com.sososhopping.server.entity.coupon.UserCouponId;
import com.sososhopping.server.entity.member.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, UserCouponId>, UserCouponRepositoryCustom {
    @EntityGraph(attributePaths = {"coupon"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserCoupon> findByUserAndCoupon(User user, Coupon coupon);

    boolean existsByUserAndCoupon(User user, Coupon coupon);
}
