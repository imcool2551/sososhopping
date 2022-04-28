package com.sososhopping.domain.coupon.service;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.coupon.dto.request.CouponRegisterDto;
import com.sososhopping.domain.coupon.dto.response.CouponResponse;
import com.sososhopping.domain.coupon.repository.CouponRepository;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCouponService {

    private final UserAuthRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

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

    public List<CouponResponse> findMyCoupons(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        List<UserCoupon> userCoupons = userCouponRepository
                .findActiveCouponsByUserAt(user, LocalDateTime.now());

        return userCoupons.stream()
                .map(userCoupon -> new CouponResponse(userCoupon.getStore(), userCoupon.getCoupon()))
                .collect(Collectors.toList());
    }

    public void deleteMyCoupon(Long userId, Long couponId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("no coupon with id " + couponId));

        UserCoupon userCoupon = userCouponRepository.findByUserAndCoupon(user, coupon)
                .orElseThrow(() -> new NotFoundException("you don't have coupon with id " + couponId));

        userCouponRepository.delete(userCoupon);
        coupon.addStock(1);
    }
}
