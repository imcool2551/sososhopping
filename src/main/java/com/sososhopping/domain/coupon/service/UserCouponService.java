package com.sososhopping.domain.coupon.service;

import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.common.error.Api409Exception;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.coupon.dto.request.CouponRegisterDto;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Store;
import com.sososhopping.domain.coupon.repository.CouponRepository;
import com.sososhopping.repository.coupon.UserCouponRepository;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCouponService {

    private final UserAuthRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final StoreRepository storeRepository;

    public Long registerCoupon(Long userId, CouponRegisterDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Coupon coupon = null;
        Long couponId = dto.getCouponId();
        String couponCode = dto.getCouponCode();
        if (couponId != null) {
            coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException("no coupon with id " + couponId));
        } else if (couponCode != null) {
            coupon = couponRepository.findByCouponCode(couponCode)
                    .orElseThrow(() -> new NotFoundException("no coupon with code " + couponCode));
        }

        if (userCouponRepository.existsByUserAndCoupon(user, coupon)) {
            throw new BadRequestException("you have already registered coupon");
        }

        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon);
        userCouponRepository.save(userCoupon);
        return userCoupon.getId();
    }

    @Transactional
    public void registerCouponByCouponId(User user, Long couponId) {
        Coupon findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new Api404Exception("쿠폰을 찾을 수 없습니다"));

        if (userCouponRepository.existsByUserAndCoupon(user, findCoupon)) {
            throw new Api409Exception("이미 발급받은 쿠폰입니다");
        }

        userCouponRepository.save(UserCoupon.createUserCoupon(user, findCoupon));
    }

    @Transactional
    public void registerCouponByCouponCode(User user, String couponCode) {

        Coupon findCoupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new Api404Exception("쿠폰을 찾을 수 없습니다"));

        if (userCouponRepository.existsByUserAndCoupon(user, findCoupon)) {
            throw new Api409Exception("이미 발급받은 쿠폰입니다");
        }

        userCouponRepository.save(UserCoupon.createUserCoupon(user, findCoupon));
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
