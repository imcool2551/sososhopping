package com.sososhopping.controller.user.coupon;

import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.user.request.coupon.CouponRegisterDto;
import com.sososhopping.common.dto.user.response.store.CouponDto;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Store;
import com.sososhopping.domain.store.repository.CouponRepository;
import com.sososhopping.repository.coupon.UserCouponRepository;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.service.user.coupon.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final UserAuthRepository userRepository;
    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @GetMapping("/api/v1/users/stores/{storeId}/coupons")
    public ApiListResponse<CouponDto> getStoreCoupons(@PathVariable Long storeId, Authentication authentication) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        LocalDateTime now = LocalDateTime.now();
        List<Coupon> coupons = couponRepository
                .findActiveCoupons(findStore, now);

        List<CouponDto> couponListDto = coupons
                .stream()
                .map(coupon -> new CouponDto(coupon))
                .collect(Collectors.toList());

        return new ApiListResponse<CouponDto>(couponListDto);
    }

    @PostMapping("/api/v1/users/my/coupons")
    public ResponseEntity registerCoupon(
            Authentication authentication,
            @RequestBody CouponRegisterDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        if (dto.getCouponId() != null) {
            userCouponService.registerCouponByCouponId(user, dto.getCouponId());
        } else if (dto.getCouponCode() != null) {
            userCouponService.registerCouponByCouponCode(user, dto.getCouponCode());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
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

