package com.sososhopping.domain.coupon.controller;

import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.dto.user.response.store.CouponDto;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.coupon.dto.request.CouponRegisterDto;
import com.sososhopping.domain.coupon.dto.response.StoreCouponResponse;
import com.sososhopping.domain.coupon.repository.CouponRepository;
import com.sososhopping.domain.coupon.service.UserCouponService;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final UserAuthRepository userRepository;
    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;

    @GetMapping("/store/{storeId}/coupon")
    public ApiResponse findStoreCoupons(@PathVariable Long storeId) {

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new NotFoundException("store with id " + storeId + " does not exist"));

        List<StoreCouponResponse> coupons = couponRepository
                .findActiveCoupons(store, LocalDateTime.now())
                .stream()
                .map(coupon -> new StoreCouponResponse(store, coupon))
                .collect(Collectors.toList());

        return new ApiResponse(coupons);
    }

    @PostMapping("/users/my/coupons")
    public ApiResponse registerCoupon(Authentication authentication,
                                      @RequestBody @Valid CouponRegisterDto dto) {

        validateCouponRegister(dto);
        Long userId = Long.parseLong(authentication.getName());
        Long couponId = userCouponService.registerCoupon(userId, dto);
        return new ApiResponse(couponId);
    }

    private void validateCouponRegister(CouponRegisterDto dto) {
        if (dto.getCouponId() == null && dto.getCouponCode() == null) {
            throw new BadRequestException("missing coupon id or coupon code");
        }
    }

    @GetMapping("/api/v1/users/my/coupons")
    public ApiListResponse<CouponDto> getMyCoupons(
            Authentication authentication,
            @RequestParam(required = false) Long storeId
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        List<UserCoupon> userCoupons = userCouponService.getMyCoupons(user, storeId);

        List<CouponDto> dtos = userCoupons
                .stream()
                .map(userCoupon -> new CouponDto(userCoupon.getCoupon()))
                .collect(Collectors.toList());

        return new ApiListResponse<CouponDto>(dtos);
    }

    @DeleteMapping("/api/v1/users/my/coupons/{couponId}")
    public void deleteMyCoupon(
            Authentication authentication,
            @PathVariable Long couponId
    ) {
        Long userId = Long.parseLong(authentication.getName());

        userCouponService.deleteMyCoupon(userId, couponId);
    }
}

