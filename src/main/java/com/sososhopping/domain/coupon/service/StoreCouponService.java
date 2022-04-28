package com.sososhopping.domain.coupon.service;

import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.coupon.dto.request.CreateCouponDto;
import com.sososhopping.domain.coupon.dto.response.CouponResponse;
import com.sososhopping.domain.coupon.dto.response.StoreCouponsResponse;
import com.sososhopping.domain.coupon.dto.response.UserCouponResponse;
import com.sososhopping.domain.coupon.repository.CouponRepository;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.CouponType;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.*;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCouponService {

    private final OwnerValidationService ownerValidationService;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;

    public Long createCoupon(Long ownerId, Long storeId, CreateCouponDto dto) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        CouponType couponType = CouponType.of(dto.getCouponType());
        String couponCode = generateCouponCode();

        Coupon coupon = dto.toEntity(store, couponType, couponCode);
        couponRepository.save(coupon);
        return coupon.getId();
    }

    private String generateCouponCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public CouponResponse findCoupon(Long ownerId, Long storeId, Long couponId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("can't find coupon with id " + couponId));

        if (!coupon.belongsTo(store)) {
            throw new ForbiddenException("coupon with id " + couponId + " does not belong to store with id " + storeId);
        }

        return new CouponResponse(store, coupon);
    }

    public StoreCouponsResponse findCoupons(Long ownerId, Long storeId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        List<Coupon> activeCoupons = couponRepository.findActiveCoupons(store, now());
        List<Coupon> scheduledCoupons = couponRepository.findScheduledCoupons(store, now());
        return new StoreCouponsResponse(store, activeCoupons, scheduledCoupons);
    }

    public UserCouponResponse findUserCoupon(Long ownerId, Long storeId, String phone, String couponCode) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new NotFoundException("user with phone number does not exist: " + phone));
        Coupon coupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new NotFoundException("coupon with coupon code does not exist: " + couponCode));

        UserCoupon userCoupon = userCouponRepository.findByUserAndCoupon(user, coupon)
                .orElseThrow(() -> new NotFoundException("user does not have coupon"));

        return new UserCouponResponse(userCoupon, user, store, coupon);
    }

    public void useUserCoupon(Long ownerId, Long storeId, Long userCouponId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new NotFoundException("userCoupon with id does not exist " + userCouponId));

        if (!userCoupon.belongsTo(store)) {
            throw new ForbiddenException("coupon does not belong to store");
        }

        userCoupon.use(now());
    }
}
