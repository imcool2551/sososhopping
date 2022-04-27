package com.sososhopping.domain.store.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.domain.store.dto.request.CreateCouponDto;
import com.sososhopping.domain.store.dto.response.StoreCouponResponse;
import com.sososhopping.domain.store.dto.response.StoreCouponsResponse;
import com.sososhopping.domain.store.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/owner/my/store/{storeId}/coupon")
    public ApiResponse createCoupon(Authentication authentication,
                                    @PathVariable Long storeId,
                                    @RequestBody @Valid CreateCouponDto dto) {

        validateDates(dto);
        Long ownerId = Long.parseLong(authentication.getName());
        Long couponId = couponService.createCoupon(ownerId, storeId, dto);
        return new ApiResponse(couponId);
    }

    private void validateDates(CreateCouponDto dto) {
        if (dto.getIssueDueDate().isBefore(dto.getIssueStartDate())) {
            throw new BadRequestException("issue due date must be after issue start date");
        }

        if (dto.getExpireDate().isBefore(dto.getIssueStartDate())) {
            throw new BadRequestException("expire date must be after issue start date");
        }

        if (dto.getExpireDate().isBefore(dto.getIssueDueDate())) {
            throw new BadRequestException("expire date must be after issue due date");
        }
    }

    @GetMapping("/owner/my/store/{storeId}/coupon/{couponId}")
    public StoreCouponResponse findCoupon(Authentication authentication,
                                          @PathVariable Long storeId,
                                          @PathVariable Long couponId) {

        Long ownerId = Long.parseLong(authentication.getName());
        return couponService.findCoupon(ownerId, storeId, couponId);
    }

    @GetMapping("/owner/my/store/{storeId}/coupon")
    public ApiResponse findCoupons(Authentication authentication, @PathVariable Long storeId) {

        Long ownerId = Long.parseLong(authentication.getName());
        StoreCouponsResponse coupons = couponService.findCoupons(ownerId, storeId);
        return new ApiResponse(coupons);
    }
}
