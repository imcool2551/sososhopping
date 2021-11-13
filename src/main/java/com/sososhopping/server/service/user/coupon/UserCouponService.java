package com.sososhopping.server.service.user.coupon;

import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.common.error.Api409Exception;
import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.coupon.UserCoupon;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.repository.coupon.CouponRepository;
import com.sososhopping.server.repository.coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public void registerCouponByCouponId(User user, Long couponId) {
        Coupon findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new Api404Exception("쿠폰을 찾을 수 없습니다"));

        if (userCouponRepository.existsByUserAndCoupon(user, findCoupon)) {
            throw new Api409Exception("이미 발급받은 쿠폰입니다");
        }

        userCouponRepository.save(UserCoupon.buildUserCoupon(user, findCoupon));
    }

    @Transactional
    public void registerCouponByCouponCode(User user, String couponCode) {

        Coupon findCoupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new Api404Exception("쿠폰을 찾을 수 없습니다"));

        if (userCouponRepository.existsByUserAndCoupon(user, findCoupon)) {
            throw new Api409Exception("이미 발급받은 쿠폰입니다");
        }

        userCouponRepository.save(UserCoupon.buildUserCoupon(user, findCoupon));
    }
}
