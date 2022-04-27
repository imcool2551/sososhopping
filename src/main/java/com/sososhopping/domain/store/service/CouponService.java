package com.sososhopping.domain.store.service;

import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.store.dto.request.CreateCouponDto;
import com.sososhopping.domain.store.dto.response.StoreCouponResponse;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.CouponType;
import com.sososhopping.entity.store.Store;
import com.sososhopping.domain.store.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final OwnerValidationService ownerValidationService;
    private final CouponRepository couponRepository;

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

    public StoreCouponResponse findCoupon(Long ownerId, Long storeId, Long couponId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("can't find coupon with id " + couponId));

        if (!coupon.belongsTo(store)) {
            throw new ForbiddenException("coupon with id " + couponId + " does not belong to store with id " + storeId);
        }

        return new StoreCouponResponse(store, coupon);
    }
}
