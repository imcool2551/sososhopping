package com.sososhopping.service.user.coupon;

import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.common.error.Api409Exception;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.member.User;
import com.sososhopping.entity.store.Store;
import com.sososhopping.repository.coupon.CouponRepository;
import com.sososhopping.repository.coupon.UserCouponRepository;
import com.sososhopping.auth.repository.UserRepository;
import com.sososhopping.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final StoreRepository storeRepository;

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

    @Transactional
    public List<UserCoupon> getMyCoupons(User user, Long storeId) {

        List<UserCoupon> userCoupons = userCouponRepository.findUsableCouponsByUser(user);

        if (storeId != null) {
            Store findStore = storeRepository
                    .findById(storeId)
                    .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

            userCoupons = userCoupons.stream()
                    .filter(userCoupon -> userCoupon.getCoupon().getStore() == findStore)
                    .collect(Collectors.toList());
        }

        return userCoupons;
    }

    @Transactional
    public void deleteMyCoupon(Long userId, Long couponId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        Coupon findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new Api404Exception("쿠폰을 찾을 수 없습니다"));

        userCouponRepository.findByUserAndCoupon(user, findCoupon)
                .ifPresentOrElse(userCoupon -> {
                    userCouponRepository.delete(userCoupon);
                    findCoupon.addStock(1);
                }, () -> {
                    throw new Api404Exception("존재하지 않는 쿠폰입니다");
                });
    }
}
